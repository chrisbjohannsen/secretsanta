package net.orbitdev.secretsanta.domain

/**
 * Models a family member
 */
class FamilyMember {
    int id
    String name
    Map<String, FamilyMember> immediateFamily
}
