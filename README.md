# SNB-Second-National-Bank-

# User:

    Attributes: ID, username, password (hashed), email, full name, date of birth, address, phone number.
    Relationships: One-to-many with Account (a user can have multiple accounts).

# Account:

    Attributes: ID, account number, account type (e.g., checking, savings), balance, creation date.
    Relationships: Many-to-one with User (each account belongs to a single user), one-to-many with Transaction.

# Transaction:

    Attributes: ID, transaction type (e.g., deposit, withdrawal, transfer), amount, timestamp.
    Relationships: Many-to-one with Account (each transaction belongs to a single account)

## End Points

# User Management:

    GET /api/users/{userId}: Retrieve user details.
    PUT /api/users/{userId}: Update user information.
   

# Authentication and Authorization:

    POST /api/auth/login: Authenticate user and generate a JWT token.
    POST /api/auth/logout: Invalidate JWT token and log the user out.
    POST /api/auth/register: Register a new user account.
    POST /api/auth/reset-password: Initiate password reset process.

# Account Management:

    POST /api/accounts: Create a new account for the authenticated user.
    GET /api/accounts/{accountId}: Retrieve account details.
    PUT /api/accounts/{accountId}: Update account information.
    DELETE /api/accounts/{accountId}: Close an account.

# Transaction Management:

    POST /api/accounts/{accountId}/transactions: Create a new transaction for the specified account.
    GET /api/accounts/{accountId}/transactions: Retrieve transaction history for the specified account.
    GET /api/transactions/{transactionId}: Retrieve details of a specific transaction.
    PUT /api/transactions/{transactionId}: Update transaction details (e.g., for refunds).
    DELETE /api/transactions/{transactionId}: Cancel a transaction.

# Round-Up Transactions:

    POST /api/accounts/{accountId}/round-up: Enable round-up feature for the specified account.
    DELETE /api/accounts/{accountId}/round-up: Disable round-up feature for the specified account.
    GET /api/accounts/{accountId}/round-up/goals: Retrieve savings goals associated with round-up transactions.
    POST /api/accounts/{accountId}/round-up/goals: Create a new savings goal for round-up transactions.
    PUT /api/accounts/{accountId}/round-up/goals/{goalId}: Update a savings goal.
    DELETE /api/accounts/{accountId}/round-up/goals/{goalId}: Delete a savings goal.
