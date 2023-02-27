package example;

import com.theokanning.openai.completion.CompletionRequest;
import com.theokanning.openai.image.CreateImageRequest;
import com.theokanning.openai.service.OpenAiService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.Duration;

/**
 * @author gushu
 * @date 2023/2/24 17:13
 */
public class ChatExample {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//        String token = System.getenv("OPENAI_TOKEN");
        String token = args == null ? System.getenv("OPENAI_TOKEN") : args[0];
        OpenAiService service = new OpenAiService(token, Duration.ofMinutes(3));
        while (true) {
            System.out.print("input something:");
            String name = br.readLine();
            if ("bye".equals(name)) {
                break;
            }
            try {
                long start = System.currentTimeMillis();
                doCompletion(service, name);
                long end = System.currentTimeMillis();
                if (end - start > 10 * 60 * 1000) {
                    System.out.print("more than 10 seconds consumed, actual time consume:" + (end - start));
                }
                System.out.println();
            } catch (Throwable ex) {
                System.err.println(ex);
            }


        }
    }

    private static void doCompletion(OpenAiService service, String name) {
        CompletionRequest completionRequest = CompletionRequest.builder()
                .model("text-davinci-003")
//                .model("text-curie-001")
                .prompt(name)
                .echo(false)
//                .stream(true)
                .user("lokia")
                .bestOf(2)
                .n(1)
                .maxTokens(2048)
                .temperature(0.1)
                .build();
        service.createCompletion(completionRequest).getChoices().forEach(item -> {
            System.out.println(item.getText());
        });
    }
}
