package org.wildfly.swarm.examples.scala

import org.jboss.shrinkwrap.api.ShrinkWrap
import org.wildfly.swarm.Swarm
import org.wildfly.swarm.jaxrs.JAXRSArchive

// @author Helio Frota
// @author Yoshimasa Tanabe
object Main extends App {

  val container = new Swarm()

  val deployment = ShrinkWrap.create(classOf[JAXRSArchive], "scala-wildfly-swarm.war")
  deployment.addClass(classOf[ScalaResource])
  deployment.addAllDependencies()
  container.start().deploy(deployment)

}
