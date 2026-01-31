package kirill5k.common.sttp.test

import cats.effect.IO
import kirill5k.common.cats.test.IOWordSpec
import kirill5k.common.test.FileReader
import org.scalatest.wordspec.AsyncWordSpec
import sttp.capabilities.fs2.Fs2Streams
import sttp.client4.*
import sttp.client4.httpclient.fs2.HttpClientFs2Backend
import sttp.client4.testing.WebSocketStreamBackendStub
import sttp.model.{Header, HeaderNames, MediaType, Method}

trait Sttp4WordSpec extends IOWordSpec {

  def fs2BackendStub: WebSocketStreamBackendStub[IO, Fs2Streams[IO]] =
    HttpClientFs2Backend.stub[IO]

  def readJson(path: String): String = FileReader.fromResources(path)

  extension (req: GenericRequest[?, ?])
    def isPost: Boolean                                 = req.method == Method.POST
    def isGet: Boolean                                  = req.method == Method.GET
    def isPut: Boolean                                  = req.method == Method.PUT
    def isDelete: Boolean                               = req.method == Method.DELETE
    def hasBearerToken(token: String): Boolean          = req.headers.contains(new Header("Authorization", s"Bearer $token"))
    def hasBody(json: String): Boolean                  = req.body.toString.contains(json)
    def hasHost(host: String): Boolean                  = req.uri.host.contains(host)
    def hasPath(path: String): Boolean                  = req.uri.path == path.split("/").filter(_.nonEmpty).toList
    def hasHeader(name: String, value: String): Boolean = req.headers.map(h => h.name -> h.value).toSet.contains(name -> value)
    def bodyContains(body: String): Boolean             = req.body.toString.contains(body)
    def hasParams(params: Map[String, String]): Boolean = params.toSet.subsetOf(req.uri.params.toMap.toSet[(String, String)])
    def isGoingTo(url: String): Boolean                 = {
      val urlParts = url.split("/")
      hasHost(urlParts.head) && req.uri.path.startsWith(urlParts.tail.filter(_.nonEmpty).toList)
    }
    def hasContentType(contentType: MediaType): Boolean =
      hasHeader(HeaderNames.ContentType, contentType.toString())
}
