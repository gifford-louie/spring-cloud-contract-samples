package contracts.beer.rest

import org.springframework.cloud.contract.spec.Contract

Contract.make {
	description("""
Represents a grumpy waiter that is too bored to sell any alcohol for anyone.
""")
	request {
		method POST()
		url '/buy'
		body(
				name: $(anyAlphaUnicode()),
				age: 25
		)
		headers {
			contentType(applicationJson())
		}
		stubMatchers {
			jsonPath('$.age', byRegex('[2-9][0-9]'))
		}
	}
	response {
		status 200
		body(
				message: "You're drunk [${fromRequest().body('$.name')}]. Go home!",
				status: $(c("NOT_OK"), p(execute('assertStatus($it)')))
		)
		headers {
			contentType(applicationJson())
		}
	}
}