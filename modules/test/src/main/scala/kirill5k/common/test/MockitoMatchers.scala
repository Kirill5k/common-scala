package kirill5k.common.test

import org.mockito.stubbing.{Answer, OngoingStubbing, Stubber}
import org.mockito.verification.VerificationMode
import org.mockito.{ArgumentMatchers, Mockito}
import org.scalatestplus.mockito.MockitoSugar

trait MockitoMatchers extends MockitoSugar:
  def times(numberOfInvocations: Int): VerificationMode = Mockito.times(numberOfInvocations)
  def never: VerificationMode                           = Mockito.never()
  def any[A]: A                                         = ArgumentMatchers.any[A]()
  def anyBoolean: Boolean                               = ArgumentMatchers.anyBoolean()
  def anyString: String                                 = ArgumentMatchers.anyString()
  def anyList[A]: List[A]                               = ArgumentMatchers.any[List[A]]()
  def anyOpt[A]: Option[A]                              = ArgumentMatchers.any[Option[A]]()
  def eqTo[A](value: A): A                              = ArgumentMatchers.eq[A](value)
  def doAnswer[A](answer: Answer[A]): Stubber           = Mockito.doAnswer(answer)
  def doThrow[A](error: Throwable): Stubber             = Mockito.doThrow(error)
  def when[A](mock: A): OngoingStubbing[A]              = Mockito.when(mock)
  def verify[A](mock: A, mode: VerificationMode): A     = Mockito.verify(mock, mode)
  def verify[A](mock: A): A                             = verify(mock, Mockito.times(1))
  def verifyNoInteractions(mocks: AnyRef*): Unit        = Mockito.verifyNoInteractions(mocks: _*)
  def verifyNoMoreInteractions(mocks: AnyRef*): Unit    = Mockito.verifyNoMoreInteractions(mocks: _*)
