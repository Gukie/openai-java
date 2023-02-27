package example;

import com.theokanning.openai.completion.CompletionRequest;
import com.theokanning.openai.image.CreateImageRequest;
import com.theokanning.openai.service.OpenAiService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author gushu
 * @date 2023/2/24 17:13
 */
public class ChatExample {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//        String token = System.getenv("OPENAI_TOKEN");
        String token = args == null ? System.getenv("OPENAI_TOKEN"): args[0];
        OpenAiService service = new OpenAiService(token);
        while (true) {
            System.out.print("input something:");
            String name = br.readLine();
            if ("bye".equals(name)) {
                break;
            }
            doCompletion(service, name);
            System.out.println();
        }
    }

    private static void doCompletion(OpenAiService service, String name) {
        CompletionRequest completionRequest = CompletionRequest.builder()
                .model("text-davinci-003")
                .prompt(name)
                .echo(true)
//                .stream(true)
                .user("lokia")
                .bestOf(2)
//                .n(1)
                .maxTokens(20)
                .build();
        service.createCompletion(completionRequest).getChoices().forEach(item ->{
            System.out.println(item.getText());
        });
    }
}
