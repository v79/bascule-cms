package org.liamjd.bascule.db.entities

import org.jetbrains.exposed.dao.*
import org.jetbrains.exposed.sql.transactions.transaction
import org.liamjd.bascule.db.dbConnections

class InputFields(id: EntityID<Long>) : LongEntity(id) {

	var refName by INPUT_FIELD.refName
	var uuid by INPUT_FIELD.uuid
	var createdOn by INPUT_FIELD.createdOn
	var lastUpdated by INPUT_FIELD.lastUpdated

	var description by INPUT_FIELD.description

	var type by RefFieldTypes referencedOn INPUT_FIELD.type

	companion object : LongEntityClass<InputFields>(INPUT_FIELD) {
		// functions go here
	}
}

class RefFieldTypes(id: EntityID<Int>) : IntEntity(id) {

	var refName by REF_FIELD_TYPE.refName

	companion object : IntEntityClass<RefFieldTypes>(REF_FIELD_TYPE) {

		fun list(): Set<RefFieldTypes> {
			val rows = transaction(dbConnections.connect()) {
				RefFieldTypes.all().toSet()
			}

			return rows;
		}

		fun get(refName: String): RefFieldTypes {
			return transaction(dbConnections.connect()) {
				RefFieldTypes.find { REF_FIELD_TYPE.refName eq refName }.first()
			}
		}


	}
}