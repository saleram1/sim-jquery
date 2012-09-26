package com.mercadolibre.apps.sim.data.bo.core


class User
implements Serializable {

  /**
   * Account is not accessible due presumably to manually locking or failed login attempts
   */
  boolean accountLocked = false
  Integer callerId
  String email = ""
  boolean enabled = true
  String firstName = ""
  String id
  Date lastLogin
  String lastName = ""
  Locale locale
  String oldEmail
  String password = ""
  TimeZone timeZone


  /**
   * Transient property, true if company.verificationCompleteDate is set
   */
  boolean verified

  String refreshToken

  Date lastUpdated
  Date dateCreated

  /**
   * The Shoppe to which the User belongs
   */
  static belongsTo = [company: Shoppe]


  //GORM
  // ensure the key to the account is ALWAYS I mean always stored toLowerCase( )
  def beforeInsert = {
    email = email.toLowerCase()
  }

  static constraints = {
	callerId()
	company(nullable: true)
    email(blank: false, unique: true, email: true)  // this is effectively the login
    firstName(nullable: false, blank: false)
    id(maxSize: 32)
    lastLogin(nullable: true)
    lastName(nullable: false, blank: false)
//    lastPasswordResetRequest(nullable: true)
    lastUpdated(nullable:true)
    locale(nullable: false)
    oldEmail(nullable: true, blank: false, email: true)
//    passwordResetToken(nullable: true, blank: false, minSize: 64, maxSize: 64)
    password(nullable: true, blank: false)
    timeZone(nullable: true)
  }

  static mapping = {
    company lazy: false
    id generator: "uuid"
  }

  static transients = ['verified']

  //METHODS
  def boolean getVerified() {
    //If company has verificationComplete date set, user is verified
    return (company?.verificationCompleteDate != null)
  }

  String toString() {
    "User: email [${email}], firstName [${firstName}], lastName [${lastName}] at Company [${company?.name}]"
  }
}