package gr.kstamatis.nlg.security

import org.apache.commons.lang.builder.HashCodeBuilder

class NLGUserNLGRole implements Serializable {

	NLGUser NLGUser
	NLGRole NLGRole

	boolean equals(other) {
		if (!(other instanceof NLGUserNLGRole)) {
			return false
		}

		other.NLGUser?.id == NLGUser?.id &&
			other.NLGRole?.id == NLGRole?.id
	}

	int hashCode() {
		def builder = new HashCodeBuilder()
		if (NLGUser) builder.append(NLGUser.id)
		if (NLGRole) builder.append(NLGRole.id)
		builder.toHashCode()
	}

	static NLGUserNLGRole get(long NLGUserId, long NLGRoleId) {
		find 'from NLGUserNLGRole where NLGUser.id=:NLGUserId and NLGRole.id=:NLGRoleId',
			[NLGUserId: NLGUserId, NLGRoleId: NLGRoleId]
	}

	static NLGUserNLGRole create(NLGUser NLGUser, NLGRole NLGRole, boolean flush = false) {
		new NLGUserNLGRole(NLGUser: NLGUser, NLGRole: NLGRole).save(flush: flush, insert: true)
	}

	static boolean remove(NLGUser NLGUser, NLGRole NLGRole, boolean flush = false) {
		NLGUserNLGRole instance = NLGUserNLGRole.findByNLGUserAndNLGRole(NLGUser, NLGRole)
		if (!instance) {
			return false
		}

		instance.delete(flush: flush)
		true
	}

	static void removeAll(NLGUser NLGUser) {
		executeUpdate 'DELETE FROM NLGUserNLGRole WHERE NLGUser=:NLGUser', [NLGUser: NLGUser]
	}

	static void removeAll(NLGRole NLGRole) {
		executeUpdate 'DELETE FROM NLGUserNLGRole WHERE NLGRole=:NLGRole', [NLGRole: NLGRole]
	}

	static mapping = {
		id composite: ['NLGRole', 'NLGUser']
		version false
	}
}
