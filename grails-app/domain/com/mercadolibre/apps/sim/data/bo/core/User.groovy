package com.mercadolibre.apps.sim.data.bo.core


class User
implements Serializable {

  /**
   * Account is not accessible due presumably to manually locking or failed login attempts
   */
  boolean accountLocked = false

   /**
   * Date when account was locked
   */
  Date accountLockedDate
  String email = ""
  boolean enabled = true
  String firstName = ""
  String id
  Date lastLogin
  String lastName = ""
  Date lastPasswordResetRequest
  Locale locale
  String login = ""
  String oldEmail
  String password = ""
  String passwordResetToken
  TimeZone timeZone


  /**
   * Transient property, true if company.verificationCompleteDate is set
   */
  boolean verified

  Date lastUpdated
  Date dateCreated

  /**
   * The Shoppe to which the User belongs
   */
  static belongsTo = [company: Shoppe]


  //GORM
  def beforeInsert = {
    email = email.toLowerCase()
    login = login.toLowerCase()
  }

  static constraints = {
    accountLockedDate(nullable: true)
    email(blank: false, unique: true, email: true)
    firstName(blank: true)
    id(maxSize: 32)
    lastLogin(nullable: true)
    lastName(blank: true)
    lastPasswordResetRequest(nullable: true)
    lastUpdated(nullable:true)
    locale(nullable: false)
    login(blank: false, unique: true)
    oldEmail(nullable: true, blank: false, email: true)
    passwordResetToken(nullable: true, blank: false, minSize: 64, maxSize: 64)
    password(blank: true)
    timeZone(nullable: true)
  }

  static mapping = {
    cache true
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
    "User: login [${this.login}], email [${email}], firstName [${firstName}], lastName [${lastName}]"
  }
}