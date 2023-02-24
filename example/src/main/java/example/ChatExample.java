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
        String token = System.getenv("OPENAI_TOKEN");
        OpenAiService service = new OpenAiService(token);
        while (true) {
            System.out.print("请输入需要完善的话：");
            String name = br.readLine();
            if ("bye".equals(name)) {
                break;
            }
            doCompletion(service, name);
        }
    }

    private static void doCompletion(OpenAiService service, String name) {
        System.out.println("\nCreating completion for: " + name + "");
        CompletionRequest completionRequest = CompletionRequest.builder()
                .model("ada")
                .prompt(name)
                .echo(true)
                .user("testing")
                .n(3)
                .build();
        service.createCompletion(completionRequest).getChoices().forEach(System.out::println);
    }
}
