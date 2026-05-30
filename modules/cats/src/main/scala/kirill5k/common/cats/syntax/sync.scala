package kirill5k.common.cats.syntax

import cats.effect.Sync
import cats.syntax.flatMap.*
import kirill5k.common.cats.CmdResult

import java.io.{File, IOException}
import scala.sys.process.*
import scala.util.chaining.scalaUtilChainingOps

object sync {
  extension [F[_]](F: Sync[F])
    def cmd(
        args: Seq[String],
        cwd: Option[File] = None,
        extraEnv: Map[String, String] = Map.empty
    ): F[CmdResult] =
      F.blocking {
        val stdout = new StringBuilder
        val stderr = new StringBuilder

        val exitCode = Process(args, cwd, extraEnv.toSeq*).!(
          ProcessLogger(
            out => { val _ = stdout.append(out).append('\n') },
            err => { val _ = stderr.append(err).append('\n') }
          )
        )

        CmdResult(exitCode, stdout.toString, stderr.toString)
      }

    def cmdOrRaise(
        args: Seq[String],
        cwd: Option[File] = None,
        extraEnv: Map[String, String] = Map.empty
    ): F[String] = {
      given Sync[F] = F
      cmd(args, cwd, extraEnv)
        .flatMap { result =>
          Either
            .cond(
              result.exitCode == 0,
              result.stdout,
              new IOException(s"cmd failed (exit ${result.exitCode}): ${result.stderr.trim}")
            )
            .pipe(F.fromEither)
        }
    }
}
