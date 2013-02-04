package gr.kstamatis.nlg.domain



import org.junit.*
import grails.test.mixin.*

@TestFor(RecordController)
@Mock(Record)
class RecordControllerTests {


    def populateValidParams(params) {
      assert params != null
      // TODO: Populate valid properties like...
      //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/record/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.recordInstanceList.size() == 0
        assert model.recordInstanceTotal == 0
    }

    void testCreate() {
       def model = controller.create()

       assert model.recordInstance != null
    }

    void testSave() {
        controller.save()

        assert model.recordInstance != null
        assert view == '/record/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/record/show/1'
        assert controller.flash.message != null
        assert Record.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/record/list'


        populateValidParams(params)
        def record = new Record(params)

        assert record.save() != null

        params.id = record.id

        def model = controller.show()

        assert model.recordInstance == record
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/record/list'


        populateValidParams(params)
        def record = new Record(params)

        assert record.save() != null

        params.id = record.id

        def model = controller.edit()

        assert model.recordInstance == record
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/record/list'

        response.reset()


        populateValidParams(params)
        def record = new Record(params)

        assert record.save() != null

        // test invalid parameters in update
        params.id = record.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/record/edit"
        assert model.recordInstance != null

        record.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/record/show/$record.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        record.clearErrors()

        populateValidParams(params)
        params.id = record.id
        params.version = -1
        controller.update()

        assert view == "/record/edit"
        assert model.recordInstance != null
        assert model.recordInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/record/list'

        response.reset()

        populateValidParams(params)
        def record = new Record(params)

        assert record.save() != null
        assert Record.count() == 1

        params.id = record.id

        controller.delete()

        assert Record.count() == 0
        assert Record.get(record.id) == null
        assert response.redirectedUrl == '/record/list'
    }
}
