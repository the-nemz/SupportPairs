function Mentor(userNames, Gender, Max, Past, Names, Age, emails, condition1, condition2, condition3, psswrd, infoParagraphs) {
	this.username = userNames;
	this.fullName = Names;
    this.age = Age
	this.email = emails;
	this.condition1 = condition1;
	this.condition2 = condition2;
	this.condition3 = condition3;
	this.password = psswrd
	this.gender = Gender;
	this.max = Max;
	this.past = Past;
	this.infoParagraph = infoParagraphs;
}

var usrName = "msaltzm5";

$.getJSON("supportpair/api/mentor/" + usrName, function (data) { alert(data) });

var fullName = "Matthew Saltzman";
var Gender = "M";
var Max = 5;
var Past = 1;
var psswrd = "abc123";
var Age = 22;
var emailInput = "mattsaltzman01@gmail.com";
var conditionsList = ["Leukemia", "Cancer", "Depression"];
var infoParagraphInput = "My Name is Matt and I was diagnosed with Leukemia was I 15, but I have been in remission for the last 2 and 1/2 years. :-)";

var MattSaltzman = new Mentor(usrName, Gender, Max, Past, fullName, Age, emailInput, conditionsList[0], conditionsList[1], conditionsList[2], psswrd, infoParagraphInput);

function writeUserName() {
	document.write(MattSaltzman.username + "\n")
}

function writeName() {
	document.write(MattSaltzman.fullName + "\n")
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

