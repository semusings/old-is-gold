#-------------------------------------------------------------------------------
Story: Membership Submission

As a member
I want to register to the site
So that I can access library resources

Type: UseCase
Actor: Librarian

Description: Librarian collects the information of the person who wishes to get membership,
fill up the information in the portal and registers for the library.

Trigger:  A person requests librarian to register for the library.

Preconditions:
1. The librarian collects required credentials and information to be registered as member.

Normal Course:
1. The librarian opens the membership database portal.
2. Fills Up the required information such as name, address, contact, ID of the member.
3. Submits the filled information to the registered member.
4. Server verifies and validate the filled information.
5. Librarians post the payment for the registered member.
6. Server verifies the authenticity of the payment for membership registration.
7. Server creates the profile for registered member.

Post conditions:
1. Newly registered member is activated and fully unlocked to use LMS service.

#-------------------------------------------------------------------------------

Feature: Membership registration

  A member is allowed to register if he:
  * provides a unique email address

  Scenario: Successful registration of a new member
  """
  If a member add an unique email with correct name, address, contact then registration should
  succeed and we should present him with a "registration confirmation" link.
  """
    Given


  Scenario: Unable to register a member if the email is already registered
  """
  If a member adds an email that is already registered, registration should fail and
  we should present him with a "forgotten password" link instead.
  """
    Given

