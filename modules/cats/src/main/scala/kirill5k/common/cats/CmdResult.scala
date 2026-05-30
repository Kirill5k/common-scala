package kirill5k.common.cats

final case class CmdResult(
    exitCode: Int,
    stdout: String,
    stderr: String
)
