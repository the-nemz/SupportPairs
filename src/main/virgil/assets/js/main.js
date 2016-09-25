var VirgilSDK;
var virgil;
var keyPair;

function init() {
	VirgilSDK = window.VirgilSDK;
    virgil = new VirgilSDK(   "AT.9c6df6c416665e0a3a6f4acc9bc4fa9f038fc57649da5a91b68ba73711413b46");
    
    keyPair = virgil.crypto.generateKeyPair();
    
    virgil.cards.create({
        public_key: keyPair.publicKey,
        private_key: keyPair.privateKey,
        private_key_password: 
    });
}

