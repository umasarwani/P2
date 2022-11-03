package com.udacity.webcrawler.profiler;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.util.Objects;

/**
 * A method interceptor that checks whether {@link Method}s are annotated with the {@link Profiled}
 * annotation. If they are, the method interceptor records how long the method invocation took.
 */
final class ProfilingMethodInterceptor implements InvocationHandler{
  private final Clock clock;
  private final Object targetObject;
  private final ProfilingState profilingState;

  // TODO: You will need to add more instance fields and constructor arguments to this class.
  ProfilingMethodInterceptor(Clock clock, Object targetObject,ProfilingState profilingState) {
    this.clock = Objects.requireNonNull(clock);
   this.targetObject=targetObject;
    this.profilingState=profilingState;

  }

  @Override
  public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
    // TODO: This method interceptor should inspect the called method to see if it is a profiled
    //       method. For profiled methods, the interceptor should record the start time, then
    //       invoke the method using the object that is being profiled. Finally, for profiled
    //       methods, the interceptor should record how long the method call took, using the
    //       ProfilingState methods.
    Object result=proxy;
    Instant start=null;

    boolean isMethodWithProfileAnnotation=method.isAnnotationPresent(Profiled.class);

    if(isMethodWithProfileAnnotation) {
      start = clock.instant();
    }
    try {
      result=method.invoke(targetObject,args);
    } catch (IllegalAccessException e) {
      throw e.getCause();
    } catch (InvocationTargetException e) {
      throw e.getTargetException();
    }finally {
      if(isMethodWithProfileAnnotation){
        Duration elapsedDuration =Duration.between(start,clock.instant());
        profilingState.record(targetObject.getClass(),method,elapsedDuration);
      }
    }
    return result;

    }

}
