package cn.gtemc.levelerbridge.api;

public class LevelerBridgeException extends RuntimeException {

    public LevelerBridgeException(String message) {
        super(message);
    }

    public LevelerBridgeException(String message, Throwable cause) {
        super(message, cause);
    }
}
