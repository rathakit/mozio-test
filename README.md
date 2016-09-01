# mozio-test
=============================================
Rathakit's Note # 31 August 2016
=============================================
●  HOW TO CHECKOUT THE PROJECT

1).  VCS > Checkout from Version Control
- Git Respository URL: https://github.com/rathakit/mozio-test.git
- Parent Directory: Specific the parent directory.
- Directory Name: Specific the directory name - Default: mozio-test

2). CLONE IT!

●  Alice is the Android library created and included in the App.

●  Please feel free to check it out and to drop me any suggestion. 
Enjoy!
---------------------------------------------
Task:
You’ve been hired to write an app that will help doctors identify how likely it is that a person has a neurological condition called Todd’s Syndrome. Use the following information to determine the likelihood: (note: although this is a real syndrome, these are just for this test and not based on real data)

●  Many patients with this disorder also have migraines

●  People 15 years old or younger are more likely to have it

●  There are more documented cases of men having it than woman

●  Usage of hallucinogenic drugs increases the probability

For simplicity sake, consider that those are the only factors that lead to a diagnosis, and that they all have the same weight. For example: someone that matches all of those factors is 100% likely to have the disorder. Matching two of them leads to a 50% probability. Matching none means 0% likely to have it.

The app should also store the past results locally for consulting at a later time.

Final Product

We give you freedom to come up with your own solution. It doesn’t have to look great. Using raw (unstyled) native elements is just fine. We’re more interested in the code and structure. Make it functional and keep it organized. Write self­documented code and/or documentation as you see fit.

Bonus Points if...

●  You also write tests

●  Your app runs in the majority of devices available (especially for Android)

●  You make the main logic reusable (as a lib for example)

●  You use transitions/animations

● Impress us: how would you switch from locally storing data to a REST API–what would that API look like? 

POST -> /register
Body
{
	"first_name": "Alex",
	"last_name": "Coldman",
	"gender": "M",
	"birthday": "10-12-1987" 
}

Response
{
	"status": 200,
	"message": "Success",
	"patient": {
		"patient_id": "1",
		"first_name": "Alex",
		"last_name": "Coldman",
		"gender": "M",
		"birthday": "10-12-1987"
	}
}
===========================
POST -> /medical-record
Body
{
	"patient_id": "1",
	"migraine_included": true,
	"hallucinogenic_drug_usage": true,
	"alice_possibility_percentage": 75,
	"record_timestamp": 1472583200 
}

Response
{
	"status": 200,
	"message": "Success"
	"medical_records": {
		"medical_record_id": "1",
		"migraine_included": true,
		"hallucinogenic_drug_usage": true,
		"alice_possibility_percentage": 75,
		"record_timestamp": 1472583200 
	}
}
===========================
GET -> /medical-records/{patient_id}

Response
{
	"status": 200,
	"message": "Success"
	"patient": {
		"patient_id": "1",
		"first_name": "Alex",
		"last_name": "Coldman",
		"gender": "M",
		"birthday": "10-12-1987"
		"medical_records": [
			{
				"medical_record_id": "2",
				"migraine_included": true,
				"hallucinogenic_drug_usage": true,
				"alice_possibility_percentage": 75,
				"record_timestamp": 1482583200 
			},
			{
				"medical_record_id": "1",
				"migraine_included": true,
				"hallucinogenic_drug_usage": false,
				"alice_possibility_percentage": 50,
				"record_timestamp": 1477383200 
			}
		]
	}
}
