package com.example.gameflixbackend.pagerender.controller;

import com.example.gameflixbackend.usermanagement.model.Transaction;
import com.example.gameflixbackend.usermanagement.model.User;
import com.example.gameflixbackend.usermanagement.repository.TransactionRepository;
import com.example.gameflixbackend.usermanagement.repository.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.Optional;

@Controller
public class SubscriptionsController {

    private final UserRepository userRepository;
    private final TransactionRepository transactionRepository;

    public SubscriptionsController(UserRepository userRepository, TransactionRepository transactionRepository) {
        this.userRepository = userRepository;
        this.transactionRepository = transactionRepository;
    }

    @GetMapping("/subscribe")
    public String renderSubscriptionPage(Model model, Principal principal) {
        String currentUsername = principal.getName();
        Optional<User> optionalUser = this.userRepository.findByUsername(currentUsername);

        if (optionalUser.isEmpty()) {
            throw new RuntimeException("Error finding user");
        }

        // we need to know if the user has subscribed or not yet
        User user = optionalUser.get();
        model.addAttribute("isSubscribed", user.getSubscribed());

        return "subscriptions";
    }

    @PostMapping("/subscribe/checkout")
    public String processSubscription(Principal principal,
                                      @RequestParam("cardName") String cardName,
                                      @RequestParam("cardNumber") String cardNumber,
                                      @RequestParam("cardExpiry") String cardExpiry) {

        String currentUsername = principal.getName();
        User user = this.userRepository.findByUsername(currentUsername)
                .orElseThrow(() -> new RuntimeException("Error finding user"));

        // make the user a subscriber
        user.setSubscribed(true);
        this.userRepository.save(user);

        // save the transaction
        String cleanCardNum = cardNumber.replaceAll("\\s+", "");
        String last4 = "0000";
        if (cleanCardNum.length() >= 4) {
            last4 = cleanCardNum.substring(cleanCardNum.length() - 4);
        }
        Transaction transaction = new Transaction(user.getId(), cardName, last4, cardExpiry,9.99);
        transactionRepository.save(transaction);

        return "subscriptions-success";
    }

    @PostMapping("/subscribe/unsubscribe")
    public String removeSubscription(Principal principal) {
        String currentUsername = principal.getName();
        Optional<User> optionalUser = this.userRepository.findByUsername(currentUsername);
        if (optionalUser.isEmpty()) {
            throw new RuntimeException("Error finding user");
        }

        User user = optionalUser.get();
        user.setSubscribed(false);
        this.userRepository.save(user);

        return  "subscriptions-success";
    }

    @GetMapping("/subscribe/success")
    public String renderSuccessfulSubscriptionPage(Model model) {
        return "subscriptions-success";
    }
}
