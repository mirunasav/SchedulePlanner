package org.example.GUI.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.example.GUI.models.Task;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TaskSerializer extends JsonSerializer<Task> {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

    @Override
    public void serialize(Task task, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("activityName", task.getActivityName());
        jsonGenerator.writeNumberField("duration", task.getDuration());
        LocalDateTime startTime = LocalDateTime.of(LocalDate.now(), task.getStartTime());
        LocalDateTime endTime = LocalDateTime.of(LocalDate.now(), task.getEndTime());
        jsonGenerator.writeStringField("startTime",startTime.format(FORMATTER));
        jsonGenerator.writeStringField("endTime", endTime.format(FORMATTER));
        jsonGenerator.writeEndObject();
    }
}
