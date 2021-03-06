/*
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
/*
 * This code was generated by https://github.com/google/apis-client-generator/
 * (build: 2018-02-26 17:53:51 UTC)
 * on 2018-03-11 at 15:49:45 UTC 
 * Modify at your own risk.
 */

package com.dev.mirco.iot.backend.messaging;

/**
 * Service definition for Messaging (v1).
 *
 * <p>
 * This is an API
 * </p>
 *
 * <p>
 * For more information about this service, see the
 * <a href="" target="_blank">API Documentation</a>
 * </p>
 *
 * <p>
 * This service uses {@link MessagingRequestInitializer} to initialize global parameters via its
 * {@link Builder}.
 * </p>
 *
 * @since 1.3
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public class Messaging extends com.google.api.client.googleapis.services.json.AbstractGoogleJsonClient {

  // Note: Leave this static initializer at the top of the file.
  static {
    com.google.api.client.util.Preconditions.checkState(
        com.google.api.client.googleapis.GoogleUtils.MAJOR_VERSION == 1 &&
        com.google.api.client.googleapis.GoogleUtils.MINOR_VERSION >= 15,
        "You are currently running with version %s of google-api-client. " +
        "You need at least version 1.15 of google-api-client to run version " +
        "1.23.0 of the messaging library.", com.google.api.client.googleapis.GoogleUtils.VERSION);
  }

  /**
   * The default encoded root URL of the service. This is determined when the library is generated
   * and normally should not be changed.
   *
   * @since 1.7
   */
  public static final String DEFAULT_ROOT_URL = "https://myApplicationId.appspot.com/_ah/api/";

  /**
   * The default encoded service path of the service. This is determined when the library is
   * generated and normally should not be changed.
   *
   * @since 1.7
   */
  public static final String DEFAULT_SERVICE_PATH = "messaging/v1/";

  /**
   * The default encoded batch path of the service. This is determined when the library is
   * generated and normally should not be changed.
   *
   * @since 1.23
   */
  public static final String DEFAULT_BATCH_PATH = "batch";

  /**
   * The default encoded base URL of the service. This is determined when the library is generated
   * and normally should not be changed.
   */
  public static final String DEFAULT_BASE_URL = DEFAULT_ROOT_URL + DEFAULT_SERVICE_PATH;

  /**
   * Constructor.
   *
   * <p>
   * Use {@link Builder} if you need to specify any of the optional parameters.
   * </p>
   *
   * @param transport HTTP transport, which should normally be:
   *        <ul>
   *        <li>Google App Engine:
   *        {@code com.google.api.client.extensions.appengine.http.UrlFetchTransport}</li>
   *        <li>Android: {@code newCompatibleTransport} from
   *        {@code com.google.api.client.extensions.android.http.AndroidHttp}</li>
   *        <li>Java: {@link com.google.api.client.googleapis.javanet.GoogleNetHttpTransport#newTrustedTransport()}
   *        </li>
   *        </ul>
   * @param jsonFactory JSON factory, which may be:
   *        <ul>
   *        <li>Jackson: {@code com.google.api.client.json.jackson2.JacksonFactory}</li>
   *        <li>Google GSON: {@code com.google.api.client.json.gson.GsonFactory}</li>
   *        <li>Android Honeycomb or higher:
   *        {@code com.google.api.client.extensions.android.json.AndroidJsonFactory}</li>
   *        </ul>
   * @param httpRequestInitializer HTTP request initializer or {@code null} for none
   * @since 1.7
   */
  public Messaging(com.google.api.client.http.HttpTransport transport, com.google.api.client.json.JsonFactory jsonFactory,
      com.google.api.client.http.HttpRequestInitializer httpRequestInitializer) {
    this(new Builder(transport, jsonFactory, httpRequestInitializer));
  }

  /**
   * @param builder builder
   */
  Messaging(Builder builder) {
    super(builder);
  }

  @Override
  protected void initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest<?> httpClientRequest) throws java.io.IOException {
    super.initialize(httpClientRequest);
  }

  /**
   * An accessor for creating requests from the MessagingEndpoint collection.
   *
   * <p>The typical use is:</p>
   * <pre>
   *   {@code Messaging messaging = new Messaging(...);}
   *   {@code Messaging.MessagingEndpoint.List request = messaging.messagingEndpoint().list(parameters ...)}
   * </pre>
   *
   * @return the resource collection
   */
  public MessagingEndpoint messagingEndpoint() {
    return new MessagingEndpoint();
  }

  /**
   * The "messagingEndpoint" collection of methods.
   */
  public class MessagingEndpoint {

    /**
     * Create a request for the method "messagingEndpoint.sendDELETE".
     *
     * This request holds the parameters needed by the messaging server.  After setting any optional
     * parameters, call the {@link SendDELETE#execute()} method to invoke the remote operation.
     *
     * @param uri
     * @return the request
     */
    public SendDELETE sendDELETE(java.lang.String uri) throws java.io.IOException {
      SendDELETE result = new SendDELETE(uri);
      initialize(result);
      return result;
    }

    public class SendDELETE extends MessagingRequest<Void> {

      private static final String REST_PATH = "sendDELETE/{uri}";

      /**
       * Create a request for the method "messagingEndpoint.sendDELETE".
       *
       * This request holds the parameters needed by the the messaging server.  After setting any
       * optional parameters, call the {@link SendDELETE#execute()} method to invoke the remote
       * operation. <p> {@link
       * SendDELETE#initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest)}
       * must be called to initialize this instance immediately after invoking the constructor. </p>
       *
       * @param uri
       * @since 1.13
       */
      protected SendDELETE(java.lang.String uri) {
        super(Messaging.this, "POST", REST_PATH, null, Void.class);
        this.uri = com.google.api.client.util.Preconditions.checkNotNull(uri, "Required parameter uri must be specified.");
      }

      @Override
      public SendDELETE setAlt(java.lang.String alt) {
        return (SendDELETE) super.setAlt(alt);
      }

      @Override
      public SendDELETE setFields(java.lang.String fields) {
        return (SendDELETE) super.setFields(fields);
      }

      @Override
      public SendDELETE setKey(java.lang.String key) {
        return (SendDELETE) super.setKey(key);
      }

      @Override
      public SendDELETE setOauthToken(java.lang.String oauthToken) {
        return (SendDELETE) super.setOauthToken(oauthToken);
      }

      @Override
      public SendDELETE setPrettyPrint(java.lang.Boolean prettyPrint) {
        return (SendDELETE) super.setPrettyPrint(prettyPrint);
      }

      @Override
      public SendDELETE setQuotaUser(java.lang.String quotaUser) {
        return (SendDELETE) super.setQuotaUser(quotaUser);
      }

      @Override
      public SendDELETE setUserIp(java.lang.String userIp) {
        return (SendDELETE) super.setUserIp(userIp);
      }

      @com.google.api.client.util.Key
      private java.lang.String uri;

      /**

       */
      public java.lang.String getUri() {
        return uri;
      }

      public SendDELETE setUri(java.lang.String uri) {
        this.uri = uri;
        return this;
      }

      @Override
      public SendDELETE set(String parameterName, Object value) {
        return (SendDELETE) super.set(parameterName, value);
      }
    }
    /**
     * Create a request for the method "messagingEndpoint.sendMessage".
     *
     * This request holds the parameters needed by the messaging server.  After setting any optional
     * parameters, call the {@link SendMessage#execute()} method to invoke the remote operation.
     *
     * @param content the {@link com.dev.mirco.iot.backend.messaging.model.Message}
     * @return the request
     */
    public SendMessage sendMessage(com.dev.mirco.iot.backend.messaging.model.Message content) throws java.io.IOException {
      SendMessage result = new SendMessage(content);
      initialize(result);
      return result;
    }

    public class SendMessage extends MessagingRequest<Void> {

      private static final String REST_PATH = "sendMessage";

      /**
       * Create a request for the method "messagingEndpoint.sendMessage".
       *
       * This request holds the parameters needed by the the messaging server.  After setting any
       * optional parameters, call the {@link SendMessage#execute()} method to invoke the remote
       * operation. <p> {@link
       * SendMessage#initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest)}
       * must be called to initialize this instance immediately after invoking the constructor. </p>
       *
       * @param content the {@link com.dev.mirco.iot.backend.messaging.model.Message}
       * @since 1.13
       */
      protected SendMessage(com.dev.mirco.iot.backend.messaging.model.Message content) {
        super(Messaging.this, "POST", REST_PATH, content, Void.class);
      }

      @Override
      public SendMessage setAlt(java.lang.String alt) {
        return (SendMessage) super.setAlt(alt);
      }

      @Override
      public SendMessage setFields(java.lang.String fields) {
        return (SendMessage) super.setFields(fields);
      }

      @Override
      public SendMessage setKey(java.lang.String key) {
        return (SendMessage) super.setKey(key);
      }

      @Override
      public SendMessage setOauthToken(java.lang.String oauthToken) {
        return (SendMessage) super.setOauthToken(oauthToken);
      }

      @Override
      public SendMessage setPrettyPrint(java.lang.Boolean prettyPrint) {
        return (SendMessage) super.setPrettyPrint(prettyPrint);
      }

      @Override
      public SendMessage setQuotaUser(java.lang.String quotaUser) {
        return (SendMessage) super.setQuotaUser(quotaUser);
      }

      @Override
      public SendMessage setUserIp(java.lang.String userIp) {
        return (SendMessage) super.setUserIp(userIp);
      }

      @Override
      public SendMessage set(String parameterName, Object value) {
        return (SendMessage) super.set(parameterName, value);
      }
    }
    /**
     * Create a request for the method "messagingEndpoint.sendNotification".
     *
     * This request holds the parameters needed by the messaging server.  After setting any optional
     * parameters, call the {@link SendNotification#execute()} method to invoke the remote operation.
     *
     * @param text
     * @return the request
     */
    public SendNotification sendNotification(java.lang.String text) throws java.io.IOException {
      SendNotification result = new SendNotification(text);
      initialize(result);
      return result;
    }

    public class SendNotification extends MessagingRequest<Void> {

      private static final String REST_PATH = "sendNotification/{text}";

      /**
       * Create a request for the method "messagingEndpoint.sendNotification".
       *
       * This request holds the parameters needed by the the messaging server.  After setting any
       * optional parameters, call the {@link SendNotification#execute()} method to invoke the remote
       * operation. <p> {@link SendNotification#initialize(com.google.api.client.googleapis.services.Abs
       * tractGoogleClientRequest)} must be called to initialize this instance immediately after
       * invoking the constructor. </p>
       *
       * @param text
       * @since 1.13
       */
      protected SendNotification(java.lang.String text) {
        super(Messaging.this, "POST", REST_PATH, null, Void.class);
        this.text = com.google.api.client.util.Preconditions.checkNotNull(text, "Required parameter text must be specified.");
      }

      @Override
      public SendNotification setAlt(java.lang.String alt) {
        return (SendNotification) super.setAlt(alt);
      }

      @Override
      public SendNotification setFields(java.lang.String fields) {
        return (SendNotification) super.setFields(fields);
      }

      @Override
      public SendNotification setKey(java.lang.String key) {
        return (SendNotification) super.setKey(key);
      }

      @Override
      public SendNotification setOauthToken(java.lang.String oauthToken) {
        return (SendNotification) super.setOauthToken(oauthToken);
      }

      @Override
      public SendNotification setPrettyPrint(java.lang.Boolean prettyPrint) {
        return (SendNotification) super.setPrettyPrint(prettyPrint);
      }

      @Override
      public SendNotification setQuotaUser(java.lang.String quotaUser) {
        return (SendNotification) super.setQuotaUser(quotaUser);
      }

      @Override
      public SendNotification setUserIp(java.lang.String userIp) {
        return (SendNotification) super.setUserIp(userIp);
      }

      @com.google.api.client.util.Key
      private java.lang.String text;

      /**

       */
      public java.lang.String getText() {
        return text;
      }

      public SendNotification setText(java.lang.String text) {
        this.text = text;
        return this;
      }

      @Override
      public SendNotification set(String parameterName, Object value) {
        return (SendNotification) super.set(parameterName, value);
      }
    }
    /**
     * Create a request for the method "messagingEndpoint.sendPUT".
     *
     * This request holds the parameters needed by the messaging server.  After setting any optional
     * parameters, call the {@link SendPUT#execute()} method to invoke the remote operation.
     *
     * @param uri
     * @return the request
     */
    public SendPUT sendPUT(java.lang.String uri) throws java.io.IOException {
      SendPUT result = new SendPUT(uri);
      initialize(result);
      return result;
    }

    public class SendPUT extends MessagingRequest<Void> {

      private static final String REST_PATH = "sendPUT/{uri}";

      /**
       * Create a request for the method "messagingEndpoint.sendPUT".
       *
       * This request holds the parameters needed by the the messaging server.  After setting any
       * optional parameters, call the {@link SendPUT#execute()} method to invoke the remote operation.
       * <p> {@link
       * SendPUT#initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest)} must
       * be called to initialize this instance immediately after invoking the constructor. </p>
       *
       * @param uri
       * @since 1.13
       */
      protected SendPUT(java.lang.String uri) {
        super(Messaging.this, "POST", REST_PATH, null, Void.class);
        this.uri = com.google.api.client.util.Preconditions.checkNotNull(uri, "Required parameter uri must be specified.");
      }

      @Override
      public SendPUT setAlt(java.lang.String alt) {
        return (SendPUT) super.setAlt(alt);
      }

      @Override
      public SendPUT setFields(java.lang.String fields) {
        return (SendPUT) super.setFields(fields);
      }

      @Override
      public SendPUT setKey(java.lang.String key) {
        return (SendPUT) super.setKey(key);
      }

      @Override
      public SendPUT setOauthToken(java.lang.String oauthToken) {
        return (SendPUT) super.setOauthToken(oauthToken);
      }

      @Override
      public SendPUT setPrettyPrint(java.lang.Boolean prettyPrint) {
        return (SendPUT) super.setPrettyPrint(prettyPrint);
      }

      @Override
      public SendPUT setQuotaUser(java.lang.String quotaUser) {
        return (SendPUT) super.setQuotaUser(quotaUser);
      }

      @Override
      public SendPUT setUserIp(java.lang.String userIp) {
        return (SendPUT) super.setUserIp(userIp);
      }

      @com.google.api.client.util.Key
      private java.lang.String uri;

      /**

       */
      public java.lang.String getUri() {
        return uri;
      }

      public SendPUT setUri(java.lang.String uri) {
        this.uri = uri;
        return this;
      }

      @Override
      public SendPUT set(String parameterName, Object value) {
        return (SendPUT) super.set(parameterName, value);
      }
    }
    /**
     * Create a request for the method "messagingEndpoint.sendUpdate".
     *
     * This request holds the parameters needed by the messaging server.  After setting any optional
     * parameters, call the {@link SendUpdate#execute()} method to invoke the remote operation.
     *
     * @param value
     * @return the request
     */
    public SendUpdate sendUpdate(java.lang.String value) throws java.io.IOException {
      SendUpdate result = new SendUpdate(value);
      initialize(result);
      return result;
    }

    public class SendUpdate extends MessagingRequest<Void> {

      private static final String REST_PATH = "sendUpdate/{value}";

      /**
       * Create a request for the method "messagingEndpoint.sendUpdate".
       *
       * This request holds the parameters needed by the the messaging server.  After setting any
       * optional parameters, call the {@link SendUpdate#execute()} method to invoke the remote
       * operation. <p> {@link
       * SendUpdate#initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest)}
       * must be called to initialize this instance immediately after invoking the constructor. </p>
       *
       * @param value
       * @since 1.13
       */
      protected SendUpdate(java.lang.String value) {
        super(Messaging.this, "POST", REST_PATH, null, Void.class);
        this.value = com.google.api.client.util.Preconditions.checkNotNull(value, "Required parameter value must be specified.");
      }

      @Override
      public SendUpdate setAlt(java.lang.String alt) {
        return (SendUpdate) super.setAlt(alt);
      }

      @Override
      public SendUpdate setFields(java.lang.String fields) {
        return (SendUpdate) super.setFields(fields);
      }

      @Override
      public SendUpdate setKey(java.lang.String key) {
        return (SendUpdate) super.setKey(key);
      }

      @Override
      public SendUpdate setOauthToken(java.lang.String oauthToken) {
        return (SendUpdate) super.setOauthToken(oauthToken);
      }

      @Override
      public SendUpdate setPrettyPrint(java.lang.Boolean prettyPrint) {
        return (SendUpdate) super.setPrettyPrint(prettyPrint);
      }

      @Override
      public SendUpdate setQuotaUser(java.lang.String quotaUser) {
        return (SendUpdate) super.setQuotaUser(quotaUser);
      }

      @Override
      public SendUpdate setUserIp(java.lang.String userIp) {
        return (SendUpdate) super.setUserIp(userIp);
      }

      @com.google.api.client.util.Key
      private java.lang.String value;

      /**

       */
      public java.lang.String getValue() {
        return value;
      }

      public SendUpdate setValue(java.lang.String value) {
        this.value = value;
        return this;
      }

      @Override
      public SendUpdate set(String parameterName, Object value) {
        return (SendUpdate) super.set(parameterName, value);
      }
    }

  }

  /**
   * Builder for {@link Messaging}.
   *
   * <p>
   * Implementation is not thread-safe.
   * </p>
   *
   * @since 1.3.0
   */
  public static final class Builder extends com.google.api.client.googleapis.services.json.AbstractGoogleJsonClient.Builder {

    /**
     * Returns an instance of a new builder.
     *
     * @param transport HTTP transport, which should normally be:
     *        <ul>
     *        <li>Google App Engine:
     *        {@code com.google.api.client.extensions.appengine.http.UrlFetchTransport}</li>
     *        <li>Android: {@code newCompatibleTransport} from
     *        {@code com.google.api.client.extensions.android.http.AndroidHttp}</li>
     *        <li>Java: {@link com.google.api.client.googleapis.javanet.GoogleNetHttpTransport#newTrustedTransport()}
     *        </li>
     *        </ul>
     * @param jsonFactory JSON factory, which may be:
     *        <ul>
     *        <li>Jackson: {@code com.google.api.client.json.jackson2.JacksonFactory}</li>
     *        <li>Google GSON: {@code com.google.api.client.json.gson.GsonFactory}</li>
     *        <li>Android Honeycomb or higher:
     *        {@code com.google.api.client.extensions.android.json.AndroidJsonFactory}</li>
     *        </ul>
     * @param httpRequestInitializer HTTP request initializer or {@code null} for none
     * @since 1.7
     */
    public Builder(com.google.api.client.http.HttpTransport transport, com.google.api.client.json.JsonFactory jsonFactory,
        com.google.api.client.http.HttpRequestInitializer httpRequestInitializer) {
      super(
          transport,
          jsonFactory,
          DEFAULT_ROOT_URL,
          DEFAULT_SERVICE_PATH,
          httpRequestInitializer,
          false);
      setBatchPath(DEFAULT_BATCH_PATH);
    }

    /** Builds a new instance of {@link Messaging}. */
    @Override
    public Messaging build() {
      return new Messaging(this);
    }

    @Override
    public Builder setRootUrl(String rootUrl) {
      return (Builder) super.setRootUrl(rootUrl);
    }

    @Override
    public Builder setServicePath(String servicePath) {
      return (Builder) super.setServicePath(servicePath);
    }

    @Override
    public Builder setBatchPath(String batchPath) {
      return (Builder) super.setBatchPath(batchPath);
    }

    @Override
    public Builder setHttpRequestInitializer(com.google.api.client.http.HttpRequestInitializer httpRequestInitializer) {
      return (Builder) super.setHttpRequestInitializer(httpRequestInitializer);
    }

    @Override
    public Builder setApplicationName(String applicationName) {
      return (Builder) super.setApplicationName(applicationName);
    }

    @Override
    public Builder setSuppressPatternChecks(boolean suppressPatternChecks) {
      return (Builder) super.setSuppressPatternChecks(suppressPatternChecks);
    }

    @Override
    public Builder setSuppressRequiredParameterChecks(boolean suppressRequiredParameterChecks) {
      return (Builder) super.setSuppressRequiredParameterChecks(suppressRequiredParameterChecks);
    }

    @Override
    public Builder setSuppressAllChecks(boolean suppressAllChecks) {
      return (Builder) super.setSuppressAllChecks(suppressAllChecks);
    }

    /**
     * Set the {@link MessagingRequestInitializer}.
     *
     * @since 1.12
     */
    public Builder setMessagingRequestInitializer(
        MessagingRequestInitializer messagingRequestInitializer) {
      return (Builder) super.setGoogleClientRequestInitializer(messagingRequestInitializer);
    }

    @Override
    public Builder setGoogleClientRequestInitializer(
        com.google.api.client.googleapis.services.GoogleClientRequestInitializer googleClientRequestInitializer) {
      return (Builder) super.setGoogleClientRequestInitializer(googleClientRequestInitializer);
    }
  }
}
