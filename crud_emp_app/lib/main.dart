import 'dart:convert';
import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;

void main() {
  runApp(EmployeeApp());
}

class EmployeeApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Employee Management',
      theme: ThemeData(primarySwatch: Colors.blue),
      home: EmployeeListPage(),
    );
  }
}

class Employee {
  final String empid;
  final String empname;
  final String deptname;

  Employee({required this.empid, required this.empname, required this.deptname});

  factory Employee.fromJson(Map<String, dynamic> json) {
    return Employee(
      empid: json['empid'],
      empname: json['empname'],
      deptname: json['deptname'],
    );
  }
}

class EmployeeListPage extends StatefulWidget {
  @override
  _EmployeeListPageState createState() => _EmployeeListPageState();
}

class _EmployeeListPageState extends State<EmployeeListPage> {
  List<Employee> employees = [];

  final TextEditingController nameController = TextEditingController();
  final TextEditingController deptController = TextEditingController();
  final TextEditingController searchIdController = TextEditingController();

  final String apiUrl = 'http://10.0.2.2:8080/api/emp'; // emulator local

  Future<void> fetchEmployees() async {
    try {
      final response = await http.get(Uri.parse(apiUrl));

      if (response.statusCode == 200) {
        final List<dynamic> employeeJson = json.decode(response.body);
        setState(() {
          employees = employeeJson.map((json) => Employee.fromJson(json)).toList();
        });
      } else {
        _showErrorDialog("Failed to load employees.");
      }
    } catch (e) {
      _showErrorDialog("Error: $e");
    }
  }

  Future<void> addEmployee(String name, String dept) async {
    try {
      final response = await http.post(
        Uri.parse(apiUrl),
        headers: {'Content-Type': 'application/json'},
        body: jsonEncode({'empname': name, 'deptname': dept}),
      );

      if (response.statusCode == 200 || response.statusCode == 201) {
        final employeeJson = json.decode(response.body);
        final addedEmp = Employee.fromJson(employeeJson);
        _showInfoDialog("Employee Added", "ID: ${addedEmp.empid}\nName: ${addedEmp.empname}\nDept: ${addedEmp.deptname}");
        fetchEmployees();
      } else {
        _showErrorDialog("Failed to add employee.");
      }
    } catch (e) {
      _showErrorDialog("Error: $e");
    }
  }

  Future<void> updateEmployee(String id, String name, String dept) async {
    try {
      final response = await http.put(
        Uri.parse('$apiUrl/$id'),
        headers: {'Content-Type': 'application/json'},
        body: jsonEncode({'empid': id, 'empname': name, 'deptname': dept}),
      );

      if (response.statusCode == 200) {
        final updatedEmp = Employee.fromJson(json.decode(response.body));
        _showInfoDialog("Employee Updated", "ID: ${updatedEmp.empid}\nName: ${updatedEmp.empname}\nDept: ${updatedEmp.deptname}");
        fetchEmployees();
      } else if (response.statusCode == 404) {
        _showErrorDialog("Employee with ID $id not found.");
      } else {
        _showErrorDialog("Failed to update employee.");
      }
    } catch (e) {
      _showErrorDialog("Error: $e");
    }
  }

  Future<void> deleteEmployee(String id) async {
    try {
      final response = await http.delete(Uri.parse('$apiUrl/$id'));

      if (response.statusCode == 200) {
        _showInfoDialog("Deleted", "Employee ID $id has been deleted.");
        fetchEmployees();
      } else if (response.statusCode == 404) {
        _showErrorDialog("Employee with ID $id not found.");
      } else {
        _showErrorDialog("Failed to delete employee.");
      }
    } catch (e) {
      _showErrorDialog("Error: $e");
    }
  }

  Future<void> fetchEmployeeById(String id) async {
    try {
      final response = await http.get(Uri.parse('$apiUrl/$id'));

      if (response.statusCode == 200) {
        final employeeJson = json.decode(response.body);
        final Employee emp = Employee.fromJson(employeeJson);
        _showEmployeeDetailsDialog(emp);
      } else if (response.statusCode == 404) {
        _showErrorDialog("Employee with ID $id not found.");
      } else {
        _showErrorDialog("Failed to fetch employee.");
      }
    } catch (e) {
      _showErrorDialog("Error: $e");
    }
  }

  @override
  void initState() {
    super.initState();
    fetchEmployees();
  }

  void _showAddEmployeeDialog() {
    nameController.clear();
    deptController.clear();
    showDialog(
      context: context,
      builder: (_) => AlertDialog(
        title: Text('Add Employee'),
        content: Column(
          mainAxisSize: MainAxisSize.min,
          children: [
            TextField(
              controller: nameController,
              decoration: InputDecoration(labelText: 'Employee Name'),
            ),
            TextField(
              controller: deptController,
              decoration: InputDecoration(labelText: 'Department'),
            ),
          ],
        ),
        actions: [
          TextButton(
            onPressed: () {
              if (nameController.text.isNotEmpty && deptController.text.isNotEmpty) {
                addEmployee(nameController.text, deptController.text);
                Navigator.pop(context);
              }
            },
            child: Text('Add'),
          ),
        ],
      ),
    );
  }

  void _showEditEmployeeDialog(Employee emp) {
    nameController.text = emp.empname;
    deptController.text = emp.deptname;

    showDialog(
      context: context,
      builder: (_) => AlertDialog(
        title: Text('Update Employee'),
        content: Column(
          mainAxisSize: MainAxisSize.min,
          children: [
            TextField(
              controller: nameController,
              decoration: InputDecoration(labelText: 'Employee Name'),
            ),
            TextField(
              controller: deptController,
              decoration: InputDecoration(labelText: 'Department'),
            ),
          ],
        ),
        actions: [
          TextButton(
            onPressed: () {
              if (nameController.text.isNotEmpty && deptController.text.isNotEmpty) {
                updateEmployee(emp.empid, nameController.text, deptController.text);
                Navigator.pop(context);
              }
            },
            child: Text('Update'),
          ),
        ],
      ),
    );
  }

  void _showSearchEmployeeDialog() {
    searchIdController.clear();
    showDialog(
      context: context,
      builder: (_) => AlertDialog(
        title: Text('Search Employee by ID'),
        content: TextField(
          controller: searchIdController,
          decoration: InputDecoration(labelText: 'Employee ID'),
          keyboardType: TextInputType.number,
        ),
        actions: [
          TextButton(
            onPressed: () {
              final id = searchIdController.text;
              if (id != null) {
                fetchEmployeeById(id);
              }
              Navigator.pop(context);
            },
            child: Text('Search'),
          ),
        ],
      ),
    );
  }

  void _showEmployeeDetailsDialog(Employee emp) {
    showDialog(
      context: context,
      builder: (_) => AlertDialog(
        title: Text('Employee Details'),
        content: Column(
          mainAxisSize: MainAxisSize.min,
          children: [
            Text('ID: ${emp.empid}'),
            Text('Name: ${emp.empname}'),
            Text('Dept: ${emp.deptname}'),
          ],
        ),
        actions: [
          TextButton(
            onPressed: () => Navigator.pop(context),
            child: Text('Close'),
          ),
        ],
      ),
    );
  }

  void _showInfoDialog(String title, String message) {
    showDialog(
      context: context,
      builder: (_) => AlertDialog(
        title: Text(title),
        content: Text(message),
        actions: [
          TextButton(
            onPressed: () => Navigator.pop(context),
            child: Text('OK'),
          ),
        ],
      ),
    );
  }

  void _showErrorDialog(String message) {
    showDialog(
      context: context,
      builder: (_) => AlertDialog(
        title: Text('Error'),
        content: Text(message),
        actions: [
          TextButton(
            onPressed: () => Navigator.pop(context),
            child: Text('OK'),
          ),
        ],
      ),
    );
  }

  Widget _buildEmployeeCard(Employee emp) {
    return Card(
      child: ListTile(
        title: Text('${emp.empid}. ${emp.empname}'),
        subtitle: Text('Dept: ${emp.deptname}'),
        trailing: Row(
          mainAxisSize: MainAxisSize.min,
          children: [
            IconButton(
              icon: Icon(Icons.edit, color: Colors.blue),
              onPressed: () => _showEditEmployeeDialog(emp),
            ),
            IconButton(
              icon: Icon(Icons.delete, color: Colors.red),
              onPressed: () => deleteEmployee(emp.empid),
            ),
          ],
        ),
      ),
    );
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('Employee Management'),
        actions: [
          IconButton(
            icon: Icon(Icons.search),
            onPressed: _showSearchEmployeeDialog,
          ),
        ],
      ),
      body: RefreshIndicator(
        onRefresh: fetchEmployees,
        child: ListView.builder(
          itemCount: employees.length,
          itemBuilder: (context, index) {
            return _buildEmployeeCard(employees[index]);
          },
        ),
      ),
      floatingActionButton: FloatingActionButton(
        onPressed: _showAddEmployeeDialog,
        child: Icon(Icons.add),
      ),
    );
  }
}
