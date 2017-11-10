package com.company;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.lexruntime.AmazonLexRuntime;
import com.amazonaws.services.lexruntime.AmazonLexRuntimeClientBuilder;
import com.amazonaws.services.lexruntime.model.PostTextRequest;
import com.amazonaws.services.lexruntime.model.PostTextResult;

import java.util.Scanner;

import static org.apache.http.util.TextUtils.isEmpty;

public class Main {

    public static void main(String[] args) {
        AmazonLexRuntime Client = AmazonLexRuntimeClientBuilder.standard().withRegion(Regions.US_EAST_1).build();
        PostTextRequest postTextRequest = new PostTextRequest();
        postTextRequest.setBotName("TestBot");
        postTextRequest.setBotAlias("testbotforrequest");
        postTextRequest.setUserId("testuser");
        Scanner sc = new Scanner(System.in);
        while (true) {
            String text = sc.nextLine().trim();
            if (isEmpty(text)) {
                break;
            }
            postTextRequest.setInputText(text);
            PostTextResult postTextResult = Client.postText(postTextRequest);
            if (postTextResult.getDialogState().startsWith("Elicit")) {
                System.out.println(postTextResult.getMessage());
            } else if (postTextResult.getDialogState().equals("ReadyForFulfillment")) {
                System.out.println(String.format("%s: %s", postTextResult.getIntentName(), postTextResult.getSlots()));
            } else {
                System.out.println(postTextResult.toString());
            }
        }
        System.out.println("Thanks. Have a good day!");
    }
}
