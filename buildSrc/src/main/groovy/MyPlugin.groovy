import org.gradle.api.Plugin
import org.gradle.api.Project

class MyPlugin implements Plugin<Project> {
    @Override
    void apply(Project project) {
        // 添加你的插件逻辑
        project.task('helloPlugin') {
            doLast {
                println "Hello from MyPlugin!"
            }
        }
    }
}