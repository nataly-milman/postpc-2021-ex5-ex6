package exercise.android.reemh.todo_items;

import junit.framework.TestCase;

import org.junit.Assert;
import org.junit.Test;

import java.util.LinkedList;

public class TodoItemsHolderImplTest {
  @Test
  public void when_addingTodoItem_then_callingListShouldHaveThisItem(){
    // setup
    TodoItemsHolderImpl holderUnderTest = new TodoItemsHolderImpl();
    Assert.assertEquals(0, holderUnderTest.getCurrentItems().size());

    // test
    holderUnderTest.addNewInProgressItem("do shopping");

    // verify
    Assert.assertEquals(1, holderUnderTest.getCurrentItems().size());
  }

  @Test
  public void when_removingTodoItem_then_callingListShouldNotHaveThisItem(){
    // setup
    TodoItemsHolderImpl holderUnderTest = new TodoItemsHolderImpl();
    Assert.assertEquals(0, holderUnderTest.getCurrentItems().size());

    // test
    holderUnderTest.addNewInProgressItem("do shopping");
    TodoItem item = holderUnderTest.getCurrentItems().get(0);
    holderUnderTest.deleteItem(item);

    // verify
    Assert.assertEquals(0, holderUnderTest.getCurrentItems().size());
  }

  @Test
  public void when_removingTodoItem_then_callingListShouldNotHaveThisButOthersUntouched() throws InterruptedException {
    // setup
    TodoItemsHolderImpl holderUnderTest = new TodoItemsHolderImpl();
    Assert.assertEquals(0, holderUnderTest.getCurrentItems().size());

    // test
    holderUnderTest.addNewInProgressItem("do shopping");
    Thread.sleep(1);
    holderUnderTest.addNewInProgressItem("don't delete");
    TodoItem item = holderUnderTest.getCurrentItems().get(1);
    holderUnderTest.deleteItem(item);

    // verify
    Assert.assertEquals(1, holderUnderTest.getCurrentItems().size());
    Assert.assertEquals("don't delete", holderUnderTest.getCurrentItems().get(0).getDescription());
  }

  @Test
  public void supports_same_name() throws InterruptedException {
    // setup
    TodoItemsHolderImpl holderUnderTest = new TodoItemsHolderImpl();
    Assert.assertEquals(0, holderUnderTest.getCurrentItems().size());

    // test
    String descriptions = "task 1";
    holderUnderTest.addNewInProgressItem(descriptions);
    Thread.sleep(10);
    holderUnderTest.addNewInProgressItem(descriptions);

    // verify
    Assert.assertEquals(2, holderUnderTest.getCurrentItems().size());
  }

  @Test
  public void multiple_addition_order() throws InterruptedException {
    // setup
    TodoItemsHolderImpl holderUnderTest = new TodoItemsHolderImpl();
    Assert.assertEquals(0, holderUnderTest.getCurrentItems().size());

    // test
    String[] descriptions = new String[]{"task 1", "task 2", "task 3"};
    for (String description : descriptions){
      holderUnderTest.addNewInProgressItem(description);
      Thread.sleep(10);
    }

    // verify
    Assert.assertEquals(descriptions.length, holderUnderTest.getCurrentItems().size());
    for (int i = 0; i<holderUnderTest.getCurrentItems().size(); i++) {
      Assert.assertEquals(descriptions[2-i], holderUnderTest.getCurrentItems().get(i).getDescription());
    }
  }

  @Test
  public void multiple_addition_order_with_Done() throws InterruptedException {
    // setup
    TodoItemsHolderImpl holderUnderTest = new TodoItemsHolderImpl();
    Assert.assertEquals(0, holderUnderTest.getCurrentItems().size());

    // test
    String[] descriptions = new String[]{"task 1", "task 2", "task 3"};
    for (String description : descriptions) {
      holderUnderTest.addNewInProgressItem(description);
      Thread.sleep(10);
    }

    holderUnderTest.markItemDone(holderUnderTest.getCurrentItems().get(1));

    // verify
    Assert.assertEquals(descriptions.length, holderUnderTest.getCurrentItems().size());

    Assert.assertEquals(descriptions[2], holderUnderTest.getCurrentItems().get(0).getDescription());
    Assert.assertEquals(descriptions[1], holderUnderTest.getCurrentItems().get(2).getDescription());
    Assert.assertEquals(descriptions[0], holderUnderTest.getCurrentItems().get(1).getDescription());
  }

  @Test
  public void multiple_addition_order_with_uncheckOfDone() throws InterruptedException {
    // setup
    TodoItemsHolderImpl holderUnderTest = new TodoItemsHolderImpl();
    Assert.assertEquals(0, holderUnderTest.getCurrentItems().size());

    // test
    String[] descriptions = new String[]{"task 1", "task 2", "task 3"};
    for (String description : descriptions) {
      holderUnderTest.addNewInProgressItem(description);
      Thread.sleep(10);
    }
    TodoItem task2 = holderUnderTest.getCurrentItems().get(1);
    holderUnderTest.markItemDone(task2);
    // this was already tested in the previous test, so no order change test in the middle
    holderUnderTest.markItemInProgress(task2);
    // verify
    // order as if no changes were made
    Assert.assertEquals(descriptions.length, holderUnderTest.getCurrentItems().size());
    for (int i = 0; i<holderUnderTest.getCurrentItems().size(); i++) {
      Assert.assertEquals(descriptions[2-i], holderUnderTest.getCurrentItems().get(i).getDescription());
    }
  }

  @Test
  public void uncheckOfDone_when_InProgress_doesnt_change_the_order() throws InterruptedException {
    // setup
    TodoItemsHolderImpl holderUnderTest = new TodoItemsHolderImpl();
    Assert.assertEquals(0, holderUnderTest.getCurrentItems().size());

    // test
    String[] descriptions = new String[]{"task 1", "task 2", "task 3"};
    for (String description : descriptions) {
      holderUnderTest.addNewInProgressItem(description);
      Thread.sleep(10);
    }
    TodoItem task2 = holderUnderTest.getCurrentItems().get(1);
    holderUnderTest.markItemInProgress(task2);
    // verify
    // order as if no changes were made
    Assert.assertEquals(descriptions.length, holderUnderTest.getCurrentItems().size());
    for (int i = 0; i<holderUnderTest.getCurrentItems().size(); i++) {
      Assert.assertEquals(descriptions[2-i], holderUnderTest.getCurrentItems().get(i).getDescription());
    }
  }

  @Test
  public void multiple_addition_order_with_Delete() throws InterruptedException {
    // setup
    TodoItemsHolderImpl holderUnderTest = new TodoItemsHolderImpl();
    Assert.assertEquals(0, holderUnderTest.getCurrentItems().size());

    // test
    String[] descriptions = new String[]{"task 1", "task 2", "task 3"};
    for (String description : descriptions) {
      holderUnderTest.addNewInProgressItem(description);
      Thread.sleep(10);
    }
    TodoItem task2 = holderUnderTest.getCurrentItems().get(1);
    holderUnderTest.deleteItem(task2);
    // verify
    // order as if no changes were made
    Assert.assertEquals(descriptions.length - 1, holderUnderTest.getCurrentItems().size());
    for (int i = 0; i<holderUnderTest.getCurrentItems().size(); i++) {
      Assert.assertNotEquals(descriptions[1], holderUnderTest.getCurrentItems().get(i).getDescription());
    }
  }

  @Test
  public void delete_unadded_item() {
    // setup
    TodoItemsHolderImpl holderUnderTest = new TodoItemsHolderImpl();
    Assert.assertEquals(0, holderUnderTest.getCurrentItems().size());

    // test
    TodoItem task2 = new TodoItem();
    task2.setDescription("test task");
    holderUnderTest.deleteItem(task2);
  }

  @Test
  public void mark_done_unadded_item() {
    // setup
    TodoItemsHolderImpl holderUnderTest = new TodoItemsHolderImpl();
    Assert.assertEquals(0, holderUnderTest.getCurrentItems().size());

    // test
    TodoItem task2 = new TodoItem();
    task2.setDescription("test task");
    holderUnderTest.markItemDone(task2);
  }
}