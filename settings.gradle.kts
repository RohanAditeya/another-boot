rootProject.name = "another-framework"

rootDir.walkTopDown().maxDepth(1).forEach {
    if (it.isDirectory && !it.name.equals(rootProject.name) && it.list()!!.contains(rootProject.buildFileName)) {
        logger.info("Found and including subProject ${it.name} in rootProject build")
        include(it.name)
    }
}