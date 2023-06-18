# MONEY MANAGER BACKEND SERVICE

### Model
1. Common: 
2. Role: id, name, users
3. User: id, first name, last name, username(email), password, avatar, budgets, role
4. Budget: id, name, transactions
5. Category: id, type, name, image, color, transactions
6. Transaction: id, name, total, location(nullable), withPerson(nullable), image(nullable), category
