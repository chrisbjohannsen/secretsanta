package net.orbitdev.secretsanta.service

import net.orbitdev.secretsanta.net.orbitdev.secretsanta.db.FamilyMemberStore
import net.orbitdev.secretsanta.net.orbitdev.secretsanta.db.SecretSantaStore

class SecretSantaService {

    FamilyMemberStore familyMemberStore
    SecretSantaStore matchStore

    SecretSantaService(FamilyMemberStore familyStore, SecretSantaStore matchStore) {
        this.familyMemberStore = familyStore
        this.matchStore = matchStore
    }



}
