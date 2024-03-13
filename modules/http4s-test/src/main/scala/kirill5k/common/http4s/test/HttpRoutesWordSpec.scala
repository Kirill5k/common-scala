package kirill5k.common.http4s.test

import cats.effect.IO
import cats.effect.unsafe.IORuntime
import fs2.Stream
import io.circe.Json
import io.circe.parser.*
import kirill5k.common.cats.test.IOMockitoMatchers
import org.http4s.{Header, Request, Response, Status}
import org.scalatest.Assertion
import org.scalatest.matchers.must.Matchers
import org.scalatest.wordspec.AnyWordSpec
import org.typelevel.ci.CIString

trait HttpRoutesWordSpec extends AnyWordSpec with Matchers with IOMockitoMatchers {

  extension (r: Request[IO])
    def withBody(body: String): r.Self   = r.withBodyStream(Stream.emits(body.getBytes().toList))
    def withJsonBody(json: Json): r.Self = withBody(json.noSpaces)
    def withAuthHeader(authHeaderValue: String = "Bearer token"): r.Self =
      r.withHeaders(r.headers.put(Header.Raw(CIString("authorization"), authHeaderValue)))

  def verifyJsonResponse(
      response: IO[Response[IO]],
      expectedStatus: Status,
      expectedBody: Option[String] = None
  ): Assertion =
    response
      .flatMap { res =>
        expectedBody match {
          case Some(expectedJson) =>
            res.as[String].map { receivedJson =>
              res.status mustBe expectedStatus
              parse(receivedJson) mustBe parse(expectedJson)
            }
          case None =>
            res.body.compile.toVector.map { receivedJson =>
              res.status mustBe expectedStatus
              receivedJson mustBe empty
            }
        }
      }
      .unsafeRunSync()(IORuntime.global)

  extension (res: IO[Response[IO]])
    infix def mustHaveStatus(expectedStatus: Status, expectedBody: Option[String] = None): Assertion =
      verifyJsonResponse(res, expectedStatus, expectedBody)

  def parseJson(jsonString: String): Json =
    parse(jsonString).getOrElse(throw new RuntimeException)
}
