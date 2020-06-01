import com.diffplug.gradle.spotless.SpotlessExtension

buildscript {
    repositories {
        gradlePluginPortal()
    }
    dependencies {
        classpath("com.diffplug.spotless:spotless-plugin-gradle:4.0.1")
    }
}

allprojects {
    repositories {
        mavenCentral()
        jcenter()
    }

    apply(plugin = "com.diffplug.gradle.spotless")
}

subprojects {
    plugins.whenPluginAdded {
        if (this is JavaPlugin) {
            configure<SpotlessExtension> {
                java {

                    /*
                     * What seems to be a secondary bug here is that even though Spotless claims the file is
                     * wrongly formatted, it is identical to the one checked into version/src/main/java.
                     */

                    // build passes on Mac, fails on Windows (claims wrong format of generated file)
                    //; // nothing

                    // build fails on Mac and Windows, claims wrong format of generated file
                    //targetExclude("$buildDir/**")

                    // build fails on Mac and Windows, claims wrong format of generated file
                    //targetExclude(files("$buildDir/**"))

                    // build fails on Mac and Windows, claims wrong format of generated file
                    //targetExclude(files("build/**"))

                    // build passes on Mac and Windows
                    //targetExclude("build/**")

                    googleJavaFormat()
                }
            }

            project.tasks.withType<SourceTask> {
                if (this.name != "spotlessJavaApply") {
                    dependsOn("spotlessJavaApply")
                }
            }
        }
    }
}
