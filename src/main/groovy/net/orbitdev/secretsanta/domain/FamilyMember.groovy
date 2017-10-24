package net.orbitdev.secretsanta.domain

/**
 * Models a family member
 */
class FamilyMember {
    int id
    String name
    Map<FamilyMember, String> immediateFamily = [:]

    @Override
    String toString(){
        return "name: ${name}\nimmediate family:\n${immediateFamily.collect { key, value -> "\t${value} : ${key}\n"}}"
    }
}
