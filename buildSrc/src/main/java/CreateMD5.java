import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;
import org.gradle.api.file.DirectoryProperty;
import org.gradle.api.file.RegularFile;
import org.gradle.api.provider.Provider;
import org.gradle.api.tasks.OutputDirectory;
import org.gradle.api.tasks.SourceTask;
import org.gradle.api.tasks.TaskAction;
import org.gradle.workers.WorkQueue;
import org.gradle.workers.WorkerExecutor;

import java.io.File;
import java.io.FileInputStream;

import javax.inject.Inject;

abstract public class CreateMD5 extends SourceTask {
    @OutputDirectory
    abstract public DirectoryProperty getDestinationDirectory();

    @Inject
    abstract public WorkerExecutor getWorkerExecutor();

    @TaskAction
    void createMD5() {
        WorkQueue workQueue = getWorkerExecutor().noIsolation();
        for (File sourceFile : getSource().getFiles()) {
            try {

                Provider<RegularFile> md5File = getDestinationDirectory().file(sourceFile.getName() + ".md5");
                workQueue.submit(GenerateMD5.class, parameters -> {
                    parameters.getSourceFile().set(sourceFile);
                    parameters.getMD5File().set(md5File);
                });
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
