﻿# SNB-Second-National-Bank-

# User:

    Attributes: ID, username, password (hashed), email, full name, date of birth, address, phone number.
    Relationships: One-to-many with Account (a user can have multiple accounts).

# Account:

    Attributes: ID, account number, account type (e.g., checking, savings), balance, creation date.
    Relationships: Many-to-one with User (each account belongs to a single user), one-to-many with Transaction.

# Transaction:

    Attributes: ID, transaction type (e.g., deposit, withdrawal, transfer), amount, timestamp.
    Relationships: Many-to-one with Account (each transaction belongs to a single account)
