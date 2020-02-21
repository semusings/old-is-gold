package pathsala.serverless

import com.intuit.karate.gatling.KarateProtocol
import com.intuit.karate.gatling.PreDef.{pauseFor, _}
import io.gatling.core.Predef._
import io.gatling.core.scenario.Simulation
import io.gatling.core.structure.ScenarioBuilder

import scala.concurrent.duration._

class StudentE2ESimulation extends Simulation {

  val protocol: KarateProtocol = karateProtocol("/student/register" -> pauseFor("post" -> 25))

  val registerStudent: ScenarioBuilder = scenario("registerStudent").
    exec(karateFeature("classpath:pathsala/serverless/student/register-student.feature"))

  setUp(
    registerStudent.inject(rampUsers(10) over (5 seconds)).protocols(protocol),
  )

}