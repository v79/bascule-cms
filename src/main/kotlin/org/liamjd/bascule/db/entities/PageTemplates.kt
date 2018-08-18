package org.liamjd.web.db.entities

import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import org.liamjd.bascule.db.entities.PAGE_TEMPLATE

class PageTemplates(id: EntityID<Long>) : LongEntity(id) {

	var refName by PAGE_TEMPLATE.refName
	var uuid by PAGE_TEMPLATE.uuid
	var createdOn by PAGE_TEMPLATE.createdOn
	var lastUpdated by PAGE_TEMPLATE.lastUpdated

	var sourceText by PAGE_TEMPLATE.sourceText

	companion object : LongEntityClass<PageTemplates>(PAGE_TEMPLATE) {

		fun get(refName: String): PageTemplates? {
			var template: PageTemplates? = null
			transaction {
				addLogger(StdOutSqlLogger)
				val result = PAGE_TEMPLATE.select {
					PAGE_TEMPLATE.refName eq refName
				}.distinct().firstOrNull()
				if (result != null) {
					template = wrapRow(result)
				}
			}
			return template
		}

		fun createPageTemplate(refName: String, source: String): Long {
			val pageTemplates = transaction {
				addLogger(StdOutSqlLogger)
				PageTemplates.new {
					this.refName = refName
					this.sourceText = source
				}
			}
			return pageTemplates.id.value
		}

	}
}