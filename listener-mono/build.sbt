bintrayPackage := "blockchain-listener-mono"
resolvers += Resolver.bintrayRepo("daonomic", "maven")

libraryDependencies += "io.daonomic.cats" %% "mono" % Versions.catsMono
