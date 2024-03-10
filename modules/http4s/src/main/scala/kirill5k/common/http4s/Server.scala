package kirill5k.common.http4s

import cats.effect.Async
import com.comcast.ip4s.{Ipv4Address, Port}
import fs2.Stream
import fs2.io.net.Network
import org.http4s.HttpRoutes
import org.http4s.ember.server.EmberServerBuilder

import scala.concurrent.duration.*

final case class ServerConfig(
    host: String,
    port: Int,
    idleTimeout: FiniteDuration = 1.hour
)

object Server:
  def ember[F[_]](config: ServerConfig, routes: HttpRoutes[F])(using F: Async[F]): F[Unit] =
    EmberServerBuilder
      .default(using F, Network.forAsync[F])
      .withHost(Ipv4Address.fromString(config.host).get)
      .withPort(Port.fromInt(config.port).get)
      .withHttpApp(routes.orNotFound)
      .withIdleTimeout(config.idleTimeout)
      .build
      .use(_ => Async[F].never)

  def serveEmber[F[_]](config: ServerConfig, routes: HttpRoutes[F])(using F: Async[F]): Stream[F, Unit] =
    Stream.eval(ember(config, routes)).drain