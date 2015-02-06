package ai.api;

/***********************************************************************************************************************
 *
 * API.AI Android SDK - client-side libraries for API.AI
 * =================================================
 *
 * Copyright (C) 2014 by Speaktoit, Inc. (https://www.speaktoit.com)
 * https://www.api.ai
 *
 ***********************************************************************************************************************
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 *
 ***********************************************************************************************************************/

import android.text.TextUtils;

public class AIConfiguration {

    private static final String SERVICE_PROD_URL="https://api.api.ai/v1/";
    private static final String SERVICE_DEV_URL = "https://dev.api.ai/api/";

    protected static final String CURRENT_PROTOCOL_VERSION = "20150204";

    protected static final String QUESTION_ENDPOINT = "query";

    private String serviceUrl;

    public enum RecognitionEngine {

        /**
         * Google Speech Recognition integrated into Android OS
         *
         * @deprecated Use System instead
         */
        @Deprecated
        Google,

        /**
         * Default system recognition
         */
        System,

        /**
         * Speaktoit recognition engine
         */
        Speaktoit
    }

    /**
     * Currently supported languages
     */
    public enum SupportedLanguages {
        English("en"),
        Russian("ru"),
        German("de"),
        Portuguese("pt"),
        PortugueseBrazil("pt-BR"),
        Spanish("es"),
        French("fr"),
        Italian("it"),
        Japanese("ja"),
        Korean("ko"),
        ChineseChina("zh-CN"),
        ChineseHongKong("zh-HK"),
        ChineseTaiwan("zh-TW");

        private final String languageTag;

        private SupportedLanguages(final String languageTag) {
            this.languageTag = languageTag;
        }

        public static SupportedLanguages fromLanguageTag(final String languageTag) {
            switch (languageTag) {
                case "en":
                    return English;
                case "ru":
                    return Russian;
                case "de":
                    return German;
                case "pt":
                    return Portuguese;
                case "pt-BR":
                    return PortugueseBrazil;
                case "es":
                    return Spanish;
                case "fr":
                    return French;
                case "it":
                    return Italian;
                case "ja":
                    return Japanese;
                case "ko":
                    return Korean;
                case "zh-CN":
                    return ChineseChina;
                case "zh-HK":
                    return ChineseHongKong;
                case "zh-TW":
                    return ChineseTaiwan;
                default:
                    return English;
            }
        }
    }

    private final String apiKey;
    private final String subscriptionKey;
    private final String language;
    private final RecognitionEngine recognitionEngine;

    /**
     * Protocol version used for api queries. Can be changed if old protocol version required.
     */
    private String protocolVersion;

    private boolean debug;
    private boolean writeSoundLog;

    public AIConfiguration(final String apiKey, final String subscriptionKey, final SupportedLanguages language, final RecognitionEngine recognitionEngine) {
        this.apiKey = apiKey;
        this.subscriptionKey = subscriptionKey;
        this.language = language.languageTag;
        this.recognitionEngine = recognitionEngine;

        protocolVersion = CURRENT_PROTOCOL_VERSION;

        if (recognitionEngine == RecognitionEngine.Speaktoit
                && language == SupportedLanguages.Korean) {
            throw new UnsupportedOperationException("Only System recognition supported for Korean language");
        }

        serviceUrl = SERVICE_PROD_URL;
    }

    public String getApiKey() {
        return apiKey;
    }

    public String getSubscriptionKey() {
        return subscriptionKey;
    }

    public String getLanguage() {
        return language;
    }

    public RecognitionEngine getRecognitionEngine() {
        return recognitionEngine;
    }

    /**
     * This flag is for testing purposes ONLY. Don't change it.
     * @return value indicating used service address
     */
    public boolean isDebug() {
        return debug;
    }

    /**
     * This flag is for testing purposes ONLY. Don't use it in your code.
     * @param debug value indicating used service address
     */
    public void setDebug(final boolean debug) {
        this.debug = debug;

        if (debug) {
            serviceUrl = SERVICE_DEV_URL;
        } else {
            serviceUrl = SERVICE_PROD_URL;
        }
    }

    /**
     * This flag is for testing purposes ONLY. Don't change it.
     * @param writeSoundLog value, indicating recorded sound will be saved in storage (if possible)
     */
    public void setWriteSoundLog(final boolean writeSoundLog) {
        this.writeSoundLog = writeSoundLog;
    }

    /**
     * This flag is for testing purposes ONLY. Don't use it in your code.
     * @return value, indicating recorded sound will be saved in storage  (if possible)
     */
    public boolean isWriteSoundLog() {
        return writeSoundLog;
    }

    /**
     * Check list of supported protocol versions on the api.ai website.
     * @return protocol version in YYYYMMDD format
     */
    public String getProtocolVersion() {
        return protocolVersion;
    }

    /**
     * Set protocol version for API queries. Must be in YYYYMMDD format.
     * @param protocolVersion Protocol version in YYYYMMDD format or empty string for the oldest version.
     *                        Check list of supported protocol versions on the api.ai website.
     */
    public void setProtocolVersion(final String protocolVersion) {
        this.protocolVersion = protocolVersion;
    }

    public String getQuestionUrl() {
        if (!TextUtils.isEmpty(protocolVersion)) {
            return String.format("%s%s?v=%s", serviceUrl, QUESTION_ENDPOINT, protocolVersion);
        } else {
            return String.format("%s%s", serviceUrl, QUESTION_ENDPOINT);
        }
    }
}
