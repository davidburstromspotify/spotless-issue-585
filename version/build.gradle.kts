plugins {
    id("java-library")
}

val constantsDir = File(project.buildDir, "generated/src/constants/java")

tasks.register("constantsGenerator") {
    val outputFile = File(constantsDir, "project/Version.java")
    outputs.file(outputFile)
    doLast {
        outputFile.parentFile.mkdirs()
        outputFile.writeText("package project;\n" +
                "public final class Version {\n" +
                "  private Version() {}\n" +
                "\n" +
                "  public static final String VERSION = \"0.0.0\";\n" +
                "}\n")
    }
}

tasks.named("compileJava").configure {
    dependsOn(tasks.named("constantsGenerator"))
}

sourceSets.main {
    java.srcDir(constantsDir)
}
