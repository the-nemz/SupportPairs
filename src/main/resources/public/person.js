function person(userNames, Names, emails, condition, infoParagraphs) {
	this.userName = userNames;
	this.Name = Names;
	this.email = emails;
	this.conditions = condition;
	this.infoParagraph = infoParagraphs;

}


var usrName = "msaltzm5";
var fullName = "Matthew Saltzman";
var emailInput = "mattsaltzman01@gmail.com";
var conditionsList = ["Leukemia", "Cancer", "Depression"];
var infoParagraphInput = "My Name is Matt and I was diagnosed with Leukemia was I 15, but I have been in remission for the last 2 and 1/2 years. :-)";

var MattSaltzman = new person(usrName, fullName, emailInput, conditionsList, infoParagraphInput);

function writeUserName() {
	document.write(MattSaltzman.userName + "\n")
}

function writeName() {
	document.write(MattSaltzman.Name + "\n")
}

function writeEmail() {
	document.write(MattSaltzman.email + "\n")
}

function writeConditions(i) {
	document.write(MattSaltzman.conditions[i] + "\n")
}

function writeInfoParagraph() {
	document.write(MattSaltzman.infoParagraph + "\n")
}

function editFullName() {
	var newName = document.getElementById("fname").value;
	MattSaltzman.Name = newName;
	alert("Name Changed Succesfully")
}

function editEmail() {
	var newEmail = document.getElementById("email").value;
	MattSaltzman.email = newEmail;
	alert("Email Changed Succesfully")
}

function appendCondition() {
	var newCond = document.getElementById("newCond").value;
	MattSaltzman.conditions.push(newCond);
	alert(MattSaltzman.conditions[MattSaltzman.conditions.length - 1] + " succesfully added")
}

function clearConditions() {
	while(MattSaltzman.conditions.length > 0) {
		MattSaltzman.conditions.pop();
	}
	//MattSaltzman.conditions.length = 0;
	alert("Conditions cleared");
}

function newInfoP() {
	var newPar = document.getElementById("infoP").value;
	MattSaltzman.infoParagraph = newPar;
	alert("personal information updated")
}

