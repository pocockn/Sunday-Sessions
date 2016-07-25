package handlers

import ratpack.handling.InjectionHandler

import javax.naming.Context

/**
 * Created by Nick on 7/10/2016.
 */
class NewSessionHandler extends InjectionHandler {

    void handle(Context ctx, StorageService hospitalService) {
        ctx.byMethod { method ->
            method.post {
                ctx.parse(Form).then { form ->
                    String name = form.name
                    String employees = form.employees
                    String address = form.address
                    if (name) {
                        def id = UUID.randomUUID()
                        hospitalService.save(new Hospital(id: id, name: name, employees: employees, address: address)).onError { error ->
                            ctx.render json([success: false, error: error.message])
                        } then {
                            ctx.render handlebarsTemplate("added-new.html")
                        }
                    } else {
                        ctx.response.status(400)
                        ctx.render(json([success: false, error: "name is required"]))
                    }
                }
            }
        }
    }
}
