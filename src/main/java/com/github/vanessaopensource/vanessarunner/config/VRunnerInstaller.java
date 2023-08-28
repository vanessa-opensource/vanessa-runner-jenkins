package com.github.vanessaopensource.vanessarunner.config;

import edu.umd.cs.findbugs.annotations.NonNull;
import hudson.Extension;
import hudson.FilePath;
import hudson.ProxyConfiguration;
import hudson.model.Node;
import hudson.model.TaskListener;
import hudson.slaves.NodeSpecific;
import hudson.tools.DownloadFromUrlInstaller;
import hudson.tools.ToolInstallation;
import lombok.val;
import org.kohsuke.stapler.DataBoundConstructor;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.URI;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Objects;

public final class VRunnerInstaller extends DownloadFromUrlInstaller {

    private static final String DEFAULT_INSTALLATION = "SNAPSHOT";

    @DataBoundConstructor
    public VRunnerInstaller(final String id) {
        super(id);
    }

    @NonNull
    public static VRunnerInstaller newDefaultInstallation() {
        return new VRunnerInstaller(VRunnerInstaller.DEFAULT_INSTALLATION);
    }

    @Override
    @NonNull
    public FilePath performInstallation(final ToolInstallation tool, final Node node, final TaskListener listener)
            throws IOException, InterruptedException {

        val expected = preferredLocation(tool, node);
        val log = listener.getLogger();

        var inst = getInstallable();
        if (inst == null) {
            log.println("Invalid tool ID " + id);
            return expected;
        }

        if (inst instanceof NodeSpecific) {
            inst = (Installable) ((NodeSpecific<?>) inst).forNode(node, listener);
        }

        if (isUpToDate(expected, inst)) {
            return expected;
        }

        val message = String.format("Unpacking %s to %s on %s", inst.url, expected.getRemote(), node.getDisplayName());
        log.println(message);

        if (installFromHub(expected, inst, log)) {
            expected.child(".installedFrom").write(inst.url, "UTF-8");

            createExecutables(expected, node, "vrunner", "src/main.os");
        }

        return expected;
    }

    private boolean installFromHub(final FilePath expected, final Installable installable, final PrintStream log) throws IOException, InterruptedException {

        val httpClient = ProxyConfiguration.newHttpClient();
        val uri = URI.create(installable.url);
        val request = ProxyConfiguration.newHttpRequestBuilder(uri)
                .GET()
                .build();

        HttpResponse<InputStream> response;
        try {
            response = httpClient.send(request, HttpResponse.BodyHandlers.ofInputStream());
        } catch (IOException e) {
            if (expected.exists()) {
                val message = String.format("Skipping installation of %s to %s: %s",
                        installable.url, expected.getRemote(), e.getLocalizedMessage());
                log.println(message);
                return false;
            } else {
                throw e;
            }
        }

        return unpackOspx(expected, response.body());
    }

    private boolean unpackOspx(final FilePath expected, final InputStream stream) throws IOException, InterruptedException {

        try {
            if (expected.exists()) {
                expected.deleteContents();
            } else {
                expected.mkdirs();
            }

            expected.unzipFrom(stream);
            val contentZip = expected.child("content.zip");
            contentZip.unzip(expected);
            contentZip.delete();

        } catch (IOException e) {
            val message = String.format("Failed to unpack ospx package %s", expected.getRemote());
            throw new IOException(message, e);
        }

        return true;
    }

    private void createExecutables(final FilePath expected, final Node node, final String name, final String path) throws IOException, InterruptedException {

        val computer = Objects.requireNonNull(node.toComputer());

        if (Boolean.FALSE.equals(computer.isUnix())) {

            val batchText = String.format(
                    "@echo off\n" +
                    "chcp 65001 >nul\n" +
                    "echo %s %%*\n" +
                    "oscript -encoding=UTF-8 \"%%~dp0\\%s\" %%* 2>&1\n" +
                    "exit /b %%ERRORLEVEL%%\n",
                name, path);
            expected.child(name + ".bat").write(batchText, "UTF-8");
        }
    }

    @Extension
    public static final class DescriptorImpl extends DownloadFromUrlInstaller.DescriptorImpl<VRunnerInstaller> {

        @NonNull
        @Override
        public String getDisplayName() {
            return "Install from hub.oscript.io";
        }

        @Override
        public boolean isApplicable(final Class<? extends ToolInstallation> toolType) {
            return toolType == VRunnerToolInstallation.class;
        }

        @Override
        public List<? extends Installable> getInstallables() {

            val x = new Installable();
            x.id = DEFAULT_INSTALLATION;
            x.name = "Vanessa-runner Latest snapshot";
            x.url = "https://hub.oscript.io/download/vanessa-runner/vanessa-runner-SNAPSHOT.ospx";

            val y = new Installable();
            y.id = "1.11.11";
            y.name = "Vanessa-runner 1.11.11";
            y.url = "https://hub.oscript.io/download/vanessa-runner/vanessa-runner-1.11.11.ospx";
            return List.of(x, y);
        }
    }
}
