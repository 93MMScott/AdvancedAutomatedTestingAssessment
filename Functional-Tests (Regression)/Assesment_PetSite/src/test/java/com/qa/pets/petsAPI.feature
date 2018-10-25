Feature: Testing Pets API

Scenario: Posting a new user
Given the user endpoint
When a user is posted
Then the respose is two zero one

Scenario: Posting a new owner
Given the owners page
When I post an owner
And I get the list
Then the new user appears on the list

Scenario: Updating an owner
Given the owner to update exists
When I update the owner
And I get the owner by lastname
Then the owner updates are shown

Scenario: Deleting an owner
Given the owner to delete exists
When I delete the owner
And I get the owner by ID
Then the owner is deleted