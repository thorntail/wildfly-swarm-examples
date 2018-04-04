package org.wildfly.swarm.examples.kotlin

import javax.ws.rs.ApplicationPath
import javax.ws.rs.core.Application

@ApplicationPath("/")
class MyApplication: Application() {
}
