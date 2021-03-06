package org.liamjd.web.model

import org.liamjd.bascule.model.InputField

class PageTemplate(val refName: String, val source: String = "") {

	val fields = mutableSetOf<InputField>()

	override fun toString(): String {
		return "PageTemplate [refName:$refName, ${fields.size} fields]"
	}
}