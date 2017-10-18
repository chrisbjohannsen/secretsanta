package net.orbitdev.secretsanta.net.orbitdev.secretsanta.db

interface ISecretSantaStore {

    void addMatch(String giver, String receiver)
    Boolean hasGiver(String receiver)
    Boolean hasReceiver(String giver)
}