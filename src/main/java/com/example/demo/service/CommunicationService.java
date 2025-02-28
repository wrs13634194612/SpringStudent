package com.example.demo.service;


import com.example.demo.controller.CommunicationController.CommunicationRequest;
import com.example.demo.entity.Communication;
import com.example.demo.entity.Customer;
import com.example.demo.repository.CommunicationRepository;
import com.example.demo.repository.CustomerRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommunicationService {

    @Autowired
    private CommunicationRepository communicationRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Transactional
    public Communication createCommunication(CommunicationRequest request) {
        Customer sender = customerRepository.findById(request.senderId())
                .orElseThrow(() -> new RuntimeException("Sender not found"));

        Customer receiver = request.receiverId() != null ?
                customerRepository.findById(request.receiverId())
                        .orElseThrow(() -> new RuntimeException("Receiver not found")) :
                null;

        Communication communication = new Communication();
        communication.setTitle(request.title());
        communication.setContent(request.content());
        communication.setSender(sender);
        communication.setReceiver(receiver);

        return communicationRepository.save(communication);
    }

    public Optional<Communication> getById(Integer id) {
        return communicationRepository.findById(id)
                .map(comm -> {
                    // 触发关联加载
                    comm.getSender().getId();
                    if(comm.getReceiver() != null) comm.getReceiver().getId();
                    return comm;
                });
    }

    public List<Communication> getAllCommunications() {
        return communicationRepository.findAll();
    }

    @Transactional
    public Communication updateCommunication(Integer id, CommunicationRequest request) {
        return communicationRepository.findById(id).map(comm -> {
            if (request.title() != null) comm.setTitle(request.title());
            if (request.content() != null) comm.setContent(request.content());

            if (request.senderId() != null) {
                Customer sender = customerRepository.findById(request.senderId())
                        .orElseThrow(() -> new RuntimeException("Sender not found"));
                comm.setSender(sender);
            }

            if (request.receiverId() != null) {
                Customer receiver = customerRepository.findById(request.receiverId())
                        .orElseThrow(() -> new RuntimeException("Receiver not found"));
                comm.setReceiver(receiver);
            } else {
                comm.setReceiver(null);
            }

            return communicationRepository.save(comm);
        }).orElseThrow(() -> new RuntimeException("Communication not found"));
    }

    @Transactional
    public void deleteCommunication(Integer id) {
        communicationRepository.deleteById(id);
    }
}