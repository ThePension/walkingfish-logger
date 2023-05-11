package ch.walkingfish.logger.walkingfishlogger.model.log;

public enum LogType {
    INFO("INFO"),
    DEBUG("DEBUG"),
    WARNING("WARNING"),
    ERROR("ERROR");

    private String type;

    LogType(String type) {
        this.type = type;
    }

    public String getType() {
        return this.type;
    }
}
