package kirill5k.common.cats

final case class CmdResult(
    exitCode: Int,
    stdout: String,
    stderr: String
) {
  def isError: Boolean   = exitCode != 0
  def isSuccess: Boolean = exitCode == 0
}
