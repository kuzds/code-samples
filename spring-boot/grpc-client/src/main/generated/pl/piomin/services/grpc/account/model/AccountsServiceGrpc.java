package pl.piomin.services.grpc.account.model;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.57.2)",
    comments = "Source: account.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class AccountsServiceGrpc {

  private AccountsServiceGrpc() {}

  public static final java.lang.String SERVICE_NAME = "model.AccountsService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<com.google.protobuf.StringValue,
      pl.piomin.services.grpc.account.model.AccountProto.Account> getFindByNumberMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "FindByNumber",
      requestType = com.google.protobuf.StringValue.class,
      responseType = pl.piomin.services.grpc.account.model.AccountProto.Account.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.google.protobuf.StringValue,
      pl.piomin.services.grpc.account.model.AccountProto.Account> getFindByNumberMethod() {
    io.grpc.MethodDescriptor<com.google.protobuf.StringValue, pl.piomin.services.grpc.account.model.AccountProto.Account> getFindByNumberMethod;
    if ((getFindByNumberMethod = AccountsServiceGrpc.getFindByNumberMethod) == null) {
      synchronized (AccountsServiceGrpc.class) {
        if ((getFindByNumberMethod = AccountsServiceGrpc.getFindByNumberMethod) == null) {
          AccountsServiceGrpc.getFindByNumberMethod = getFindByNumberMethod =
              io.grpc.MethodDescriptor.<com.google.protobuf.StringValue, pl.piomin.services.grpc.account.model.AccountProto.Account>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "FindByNumber"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.google.protobuf.StringValue.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  pl.piomin.services.grpc.account.model.AccountProto.Account.getDefaultInstance()))
              .setSchemaDescriptor(new AccountsServiceMethodDescriptorSupplier("FindByNumber"))
              .build();
        }
      }
    }
    return getFindByNumberMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.google.protobuf.Int32Value,
      pl.piomin.services.grpc.account.model.AccountProto.Accounts> getFindByCustomerMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "FindByCustomer",
      requestType = com.google.protobuf.Int32Value.class,
      responseType = pl.piomin.services.grpc.account.model.AccountProto.Accounts.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.google.protobuf.Int32Value,
      pl.piomin.services.grpc.account.model.AccountProto.Accounts> getFindByCustomerMethod() {
    io.grpc.MethodDescriptor<com.google.protobuf.Int32Value, pl.piomin.services.grpc.account.model.AccountProto.Accounts> getFindByCustomerMethod;
    if ((getFindByCustomerMethod = AccountsServiceGrpc.getFindByCustomerMethod) == null) {
      synchronized (AccountsServiceGrpc.class) {
        if ((getFindByCustomerMethod = AccountsServiceGrpc.getFindByCustomerMethod) == null) {
          AccountsServiceGrpc.getFindByCustomerMethod = getFindByCustomerMethod =
              io.grpc.MethodDescriptor.<com.google.protobuf.Int32Value, pl.piomin.services.grpc.account.model.AccountProto.Accounts>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "FindByCustomer"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.google.protobuf.Int32Value.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  pl.piomin.services.grpc.account.model.AccountProto.Accounts.getDefaultInstance()))
              .setSchemaDescriptor(new AccountsServiceMethodDescriptorSupplier("FindByCustomer"))
              .build();
        }
      }
    }
    return getFindByCustomerMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.google.protobuf.Empty,
      pl.piomin.services.grpc.account.model.AccountProto.Accounts> getFindAllMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "FindAll",
      requestType = com.google.protobuf.Empty.class,
      responseType = pl.piomin.services.grpc.account.model.AccountProto.Accounts.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.google.protobuf.Empty,
      pl.piomin.services.grpc.account.model.AccountProto.Accounts> getFindAllMethod() {
    io.grpc.MethodDescriptor<com.google.protobuf.Empty, pl.piomin.services.grpc.account.model.AccountProto.Accounts> getFindAllMethod;
    if ((getFindAllMethod = AccountsServiceGrpc.getFindAllMethod) == null) {
      synchronized (AccountsServiceGrpc.class) {
        if ((getFindAllMethod = AccountsServiceGrpc.getFindAllMethod) == null) {
          AccountsServiceGrpc.getFindAllMethod = getFindAllMethod =
              io.grpc.MethodDescriptor.<com.google.protobuf.Empty, pl.piomin.services.grpc.account.model.AccountProto.Accounts>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "FindAll"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.google.protobuf.Empty.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  pl.piomin.services.grpc.account.model.AccountProto.Accounts.getDefaultInstance()))
              .setSchemaDescriptor(new AccountsServiceMethodDescriptorSupplier("FindAll"))
              .build();
        }
      }
    }
    return getFindAllMethod;
  }

  private static volatile io.grpc.MethodDescriptor<pl.piomin.services.grpc.account.model.AccountProto.Account,
      pl.piomin.services.grpc.account.model.AccountProto.Account> getAddAccountMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "AddAccount",
      requestType = pl.piomin.services.grpc.account.model.AccountProto.Account.class,
      responseType = pl.piomin.services.grpc.account.model.AccountProto.Account.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<pl.piomin.services.grpc.account.model.AccountProto.Account,
      pl.piomin.services.grpc.account.model.AccountProto.Account> getAddAccountMethod() {
    io.grpc.MethodDescriptor<pl.piomin.services.grpc.account.model.AccountProto.Account, pl.piomin.services.grpc.account.model.AccountProto.Account> getAddAccountMethod;
    if ((getAddAccountMethod = AccountsServiceGrpc.getAddAccountMethod) == null) {
      synchronized (AccountsServiceGrpc.class) {
        if ((getAddAccountMethod = AccountsServiceGrpc.getAddAccountMethod) == null) {
          AccountsServiceGrpc.getAddAccountMethod = getAddAccountMethod =
              io.grpc.MethodDescriptor.<pl.piomin.services.grpc.account.model.AccountProto.Account, pl.piomin.services.grpc.account.model.AccountProto.Account>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "AddAccount"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  pl.piomin.services.grpc.account.model.AccountProto.Account.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  pl.piomin.services.grpc.account.model.AccountProto.Account.getDefaultInstance()))
              .setSchemaDescriptor(new AccountsServiceMethodDescriptorSupplier("AddAccount"))
              .build();
        }
      }
    }
    return getAddAccountMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static AccountsServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<AccountsServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<AccountsServiceStub>() {
        @java.lang.Override
        public AccountsServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new AccountsServiceStub(channel, callOptions);
        }
      };
    return AccountsServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static AccountsServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<AccountsServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<AccountsServiceBlockingStub>() {
        @java.lang.Override
        public AccountsServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new AccountsServiceBlockingStub(channel, callOptions);
        }
      };
    return AccountsServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static AccountsServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<AccountsServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<AccountsServiceFutureStub>() {
        @java.lang.Override
        public AccountsServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new AccountsServiceFutureStub(channel, callOptions);
        }
      };
    return AccountsServiceFutureStub.newStub(factory, channel);
  }

  /**
   */
  public interface AsyncService {

    /**
     */
    default void findByNumber(com.google.protobuf.StringValue request,
        io.grpc.stub.StreamObserver<pl.piomin.services.grpc.account.model.AccountProto.Account> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getFindByNumberMethod(), responseObserver);
    }

    /**
     */
    default void findByCustomer(com.google.protobuf.Int32Value request,
        io.grpc.stub.StreamObserver<pl.piomin.services.grpc.account.model.AccountProto.Accounts> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getFindByCustomerMethod(), responseObserver);
    }

    /**
     */
    default void findAll(com.google.protobuf.Empty request,
        io.grpc.stub.StreamObserver<pl.piomin.services.grpc.account.model.AccountProto.Accounts> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getFindAllMethod(), responseObserver);
    }

    /**
     */
    default void addAccount(pl.piomin.services.grpc.account.model.AccountProto.Account request,
        io.grpc.stub.StreamObserver<pl.piomin.services.grpc.account.model.AccountProto.Account> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getAddAccountMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service AccountsService.
   */
  public static abstract class AccountsServiceImplBase
      implements io.grpc.BindableService, AsyncService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return AccountsServiceGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service AccountsService.
   */
  public static final class AccountsServiceStub
      extends io.grpc.stub.AbstractAsyncStub<AccountsServiceStub> {
    private AccountsServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected AccountsServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new AccountsServiceStub(channel, callOptions);
    }

    /**
     */
    public void findByNumber(com.google.protobuf.StringValue request,
        io.grpc.stub.StreamObserver<pl.piomin.services.grpc.account.model.AccountProto.Account> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getFindByNumberMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void findByCustomer(com.google.protobuf.Int32Value request,
        io.grpc.stub.StreamObserver<pl.piomin.services.grpc.account.model.AccountProto.Accounts> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getFindByCustomerMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void findAll(com.google.protobuf.Empty request,
        io.grpc.stub.StreamObserver<pl.piomin.services.grpc.account.model.AccountProto.Accounts> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getFindAllMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void addAccount(pl.piomin.services.grpc.account.model.AccountProto.Account request,
        io.grpc.stub.StreamObserver<pl.piomin.services.grpc.account.model.AccountProto.Account> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getAddAccountMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service AccountsService.
   */
  public static final class AccountsServiceBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<AccountsServiceBlockingStub> {
    private AccountsServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected AccountsServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new AccountsServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public pl.piomin.services.grpc.account.model.AccountProto.Account findByNumber(com.google.protobuf.StringValue request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getFindByNumberMethod(), getCallOptions(), request);
    }

    /**
     */
    public pl.piomin.services.grpc.account.model.AccountProto.Accounts findByCustomer(com.google.protobuf.Int32Value request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getFindByCustomerMethod(), getCallOptions(), request);
    }

    /**
     */
    public pl.piomin.services.grpc.account.model.AccountProto.Accounts findAll(com.google.protobuf.Empty request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getFindAllMethod(), getCallOptions(), request);
    }

    /**
     */
    public pl.piomin.services.grpc.account.model.AccountProto.Account addAccount(pl.piomin.services.grpc.account.model.AccountProto.Account request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getAddAccountMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service AccountsService.
   */
  public static final class AccountsServiceFutureStub
      extends io.grpc.stub.AbstractFutureStub<AccountsServiceFutureStub> {
    private AccountsServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected AccountsServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new AccountsServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<pl.piomin.services.grpc.account.model.AccountProto.Account> findByNumber(
        com.google.protobuf.StringValue request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getFindByNumberMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<pl.piomin.services.grpc.account.model.AccountProto.Accounts> findByCustomer(
        com.google.protobuf.Int32Value request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getFindByCustomerMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<pl.piomin.services.grpc.account.model.AccountProto.Accounts> findAll(
        com.google.protobuf.Empty request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getFindAllMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<pl.piomin.services.grpc.account.model.AccountProto.Account> addAccount(
        pl.piomin.services.grpc.account.model.AccountProto.Account request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getAddAccountMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_FIND_BY_NUMBER = 0;
  private static final int METHODID_FIND_BY_CUSTOMER = 1;
  private static final int METHODID_FIND_ALL = 2;
  private static final int METHODID_ADD_ACCOUNT = 3;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final AsyncService serviceImpl;
    private final int methodId;

    MethodHandlers(AsyncService serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_FIND_BY_NUMBER:
          serviceImpl.findByNumber((com.google.protobuf.StringValue) request,
              (io.grpc.stub.StreamObserver<pl.piomin.services.grpc.account.model.AccountProto.Account>) responseObserver);
          break;
        case METHODID_FIND_BY_CUSTOMER:
          serviceImpl.findByCustomer((com.google.protobuf.Int32Value) request,
              (io.grpc.stub.StreamObserver<pl.piomin.services.grpc.account.model.AccountProto.Accounts>) responseObserver);
          break;
        case METHODID_FIND_ALL:
          serviceImpl.findAll((com.google.protobuf.Empty) request,
              (io.grpc.stub.StreamObserver<pl.piomin.services.grpc.account.model.AccountProto.Accounts>) responseObserver);
          break;
        case METHODID_ADD_ACCOUNT:
          serviceImpl.addAccount((pl.piomin.services.grpc.account.model.AccountProto.Account) request,
              (io.grpc.stub.StreamObserver<pl.piomin.services.grpc.account.model.AccountProto.Account>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  public static final io.grpc.ServerServiceDefinition bindService(AsyncService service) {
    return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
        .addMethod(
          getFindByNumberMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.google.protobuf.StringValue,
              pl.piomin.services.grpc.account.model.AccountProto.Account>(
                service, METHODID_FIND_BY_NUMBER)))
        .addMethod(
          getFindByCustomerMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.google.protobuf.Int32Value,
              pl.piomin.services.grpc.account.model.AccountProto.Accounts>(
                service, METHODID_FIND_BY_CUSTOMER)))
        .addMethod(
          getFindAllMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.google.protobuf.Empty,
              pl.piomin.services.grpc.account.model.AccountProto.Accounts>(
                service, METHODID_FIND_ALL)))
        .addMethod(
          getAddAccountMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              pl.piomin.services.grpc.account.model.AccountProto.Account,
              pl.piomin.services.grpc.account.model.AccountProto.Account>(
                service, METHODID_ADD_ACCOUNT)))
        .build();
  }

  private static abstract class AccountsServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    AccountsServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return pl.piomin.services.grpc.account.model.AccountProto.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("AccountsService");
    }
  }

  private static final class AccountsServiceFileDescriptorSupplier
      extends AccountsServiceBaseDescriptorSupplier {
    AccountsServiceFileDescriptorSupplier() {}
  }

  private static final class AccountsServiceMethodDescriptorSupplier
      extends AccountsServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final java.lang.String methodName;

    AccountsServiceMethodDescriptorSupplier(java.lang.String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (AccountsServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new AccountsServiceFileDescriptorSupplier())
              .addMethod(getFindByNumberMethod())
              .addMethod(getFindByCustomerMethod())
              .addMethod(getFindAllMethod())
              .addMethod(getAddAccountMethod())
              .build();
        }
      }
    }
    return result;
  }
}
