rootProject.name = "another-framework"

val projectsToIgnore: List<String> = listOf(rootProject.name, "buildSrc")

rootDir.walkTopDown().maxDepth(1).forEach {
    if (it.isDirectory && !projectsToIgnore.contains(it.name) && it.list()!!.contains(rootProject.buildFileName)) {
        logger.info("Found and including subProject ${it.name} in rootProject build")
        include(it.name)
    }
}